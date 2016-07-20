package com.footprint.server.common.page.mybatis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.binding.MapperMethod.ParamMap;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.footprint.server.common.page.PageParam;

public class PageUtils {

	private static Logger logger = LoggerFactory.getLogger(PageUtils.class);
	
	/**
	 * 在方法参数中查找 分页请求对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T findObjectFromParams(Object params,Class<T> objClass) {
		if (params == null) {
			return null;
		}
				
		if(objClass.isAssignableFrom(params.getClass())) {
			return (T) params;					
		}
		
		if (params instanceof ParamMap) {
			ParamMap<Object> paramMap = (ParamMap<Object>) params;
			for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
				Object paramValue = entry.getValue();
				
				if(paramValue != null && objClass.isAssignableFrom(paramValue.getClass())) {
					return (T) paramValue;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * 查询总记录数
	 */
	public static int queryTotal(String sql, MappedStatement mappedStatement,
            BoundSql boundSql, String dialect) throws SQLException {
		
		Connection connection = null;
        PreparedStatement countStmt = null;
        ResultSet rs = null;
        try {

        	connection = mappedStatement.getConfiguration().getEnvironment().getDataSource().getConnection();
        	
    		String countSql = getCountSql(sql);
       
            countStmt = connection.prepareStatement(countSql);
            BoundSql countBoundSql = copyFromBoundSql(mappedStatement, boundSql, countSql);
            setParameters(countStmt, mappedStatement, countBoundSql, boundSql.getParameterObject());
            
            rs = countStmt.executeQuery();
            int totalCount = 0;
            if (rs.next()) {
                totalCount = rs.getInt(1);
            }

            return totalCount;
        } catch (SQLException e) {
            logger.error("查询总记录数出错", e);
            throw e;
        } finally {
        	if (rs != null) {
	        	try {
	                rs.close();
	            } catch (SQLException e) {
	                logger.error("exception happens when doing: ResultSet.close()", e);
	            }
        	}
        	
        	if (countStmt != null) {
	            try {
	                countStmt.close();
	            } catch (SQLException e) {
	                logger.error("exception happens when doing: PreparedStatement.close()", e);
	            }
        	}
        	
        	if (connection != null) {
	            try {
	            	connection.close();
	            } catch (SQLException e) {
	                logger.error("exception happens when doing: Connection.close()", e);
	            }
        	}
        }
		
	}
	
	public static MappedStatement copyMappedStatement(MappedStatement ms, BoundSql boundSql, String sql) {
		BoundSql newBoundSql = copyFromBoundSql(ms, boundSql, sql);
		return copyFromMappedStatement(ms, new BoundSqlSqlSource(newBoundSql));
	}
	
	/**
	 * 删除sql中的分号
	 */
	public static String removeEnd(String sql, String s) {
		final StringBuilder sqlBuilder = new StringBuilder(sql);
		if(sqlBuilder.lastIndexOf(s) == sqlBuilder.length()-1){
            sqlBuilder.deleteCharAt(sqlBuilder.length()-1);
        }
        
		return sqlBuilder.toString();
	}
	
	/*
     * 对SQL参数(?)设值
     */
    private static void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql,
            Object parameterObject) throws SQLException {
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
        parameterHandler.setParameters(ps);
    }
	
	private static class BoundSqlSqlSource implements SqlSource {
		BoundSql boundSql;
		public BoundSqlSqlSource(BoundSql boundSql) {
			this.boundSql = boundSql;
		}
		public BoundSql getBoundSql(Object parameterObject) {
			return boundSql;
		}
	}
	
	private static BoundSql copyFromBoundSql(MappedStatement ms, BoundSql boundSql,
			String sql) {
		BoundSql newBoundSql = new BoundSql(ms.getConfiguration(),sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
		for (ParameterMapping mapping : boundSql.getParameterMappings()) {
		    String prop = mapping.getProperty();
		    if (boundSql.hasAdditionalParameter(prop)) {
		        newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
		    }
		}
		return newBoundSql;
	}
	
	//see: MapperBuilderAssistant
	private static MappedStatement copyFromMappedStatement(MappedStatement ms,SqlSource newSqlSource) {
		Builder builder = new Builder(ms.getConfiguration(),ms.getId(),newSqlSource,ms.getSqlCommandType());
		
		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
		if(ms.getKeyProperties() != null && ms.getKeyProperties().length !=0){
            StringBuffer keyProperties = new StringBuffer();
            for(String keyProperty : ms.getKeyProperties()){
                keyProperties.append(keyProperty).append(",");
            }
            keyProperties.delete(keyProperties.length()-1, keyProperties.length());
			builder.keyProperty(keyProperties.toString());
		}
		
		//setStatementTimeout()
		builder.timeout(ms.getTimeout());
		
		//setStatementResultMap()
		builder.parameterMap(ms.getParameterMap());
		
		//setStatementResultMap()
        builder.resultMaps(ms.getResultMaps());
		builder.resultSetType(ms.getResultSetType());
	    
		//setStatementCache()
		builder.cache(ms.getCache());
		builder.flushCacheRequired(ms.isFlushCacheRequired());
		builder.useCache(ms.isUseCache());
		
		return builder.build();
	}
	
    public static String getCountSql(String sql) {
        return "select count(0) from (" + sql + ") tmp_count";
    }
    
    public static String getPageSql(String sql, String dialect, PageParam pageParam) {
        StringBuilder pageSql = new StringBuilder(200);
        if ("mysql".equals(dialect)) {
            pageSql.append("select * from (");
            pageSql.append(sql);
            pageSql.append(") as tmp_page limit " + pageParam.getStartRow() + "," + pageParam.getPageSize());
        } else if ("hsqldb".equals(dialect)) {
            pageSql.append(sql);
            pageSql.append(" LIMIT " + pageParam.getPageSize() + " OFFSET " + pageParam.getStartRow());
        } else if ("oracle".equals(dialect)) {
            pageSql.append("select * from ( select temp.*, rownum row_id from ( ");
            pageSql.append(sql);
            pageSql.append(" ) temp where rownum <= ").append(pageParam.getEndRow());
            pageSql.append(") where row_id > ").append(pageParam.getStartRow());
        }
        return pageSql.toString();
    }
}

package com.footprint.server.common.page.mybatis;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.footprint.server.common.page.PageParam;
import com.footprint.server.common.page.PageResult;

@Intercepts({ 
	@Signature(type = Executor.class, method = "query", args = {
		MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class 
	}) 
})
public class PagePlugin implements Interceptor {

	private static Logger logger = LoggerFactory.getLogger(PagePlugin.class);
	
	private static int MAPPED_STATEMENT_INDEX = 0;
	private static int PARAMETER_INDEX = 1;
	private static int ROWBOUNDS_INDEX = 2;
	
	private String dialect;
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
        final Object[] args = invocation.getArgs();
        final Object parameter = args[PARAMETER_INDEX];
        
        PageParam pageParam = PageUtils.findObjectFromParams(parameter,PageParam.class);
        if(pageParam == null) {
        	return invocation.proceed();
        }
        logger.info("Mybatis page starting");
        final MappedStatement ms = (MappedStatement)args[MAPPED_STATEMENT_INDEX];
		final BoundSql boundSql = ms.getBoundSql(parameter);
		String sql = PageUtils.removeEnd(boundSql.getSql().trim(), ";");

        final int total = PageUtils.queryTotal(sql, ms, boundSql, dialect);
        
		String pageSql = PageUtils.getPageSql(sql, dialect, pageParam);			
		logger.info("Mybatis page sql: {}", pageSql);
		args[ROWBOUNDS_INDEX] = new RowBounds(RowBounds.NO_ROW_OFFSET,RowBounds.NO_ROW_LIMIT);
		args[MAPPED_STATEMENT_INDEX] = PageUtils.copyMappedStatement(ms, boundSql, pageSql);
		
		Object ret = invocation.proceed();
		@SuppressWarnings("unchecked")
		PageResult<?> pageResult = new PageResult<Object>(pageParam.getPageNo(), pageParam.getPageSize(), total, (List<Object>)ret);
		logger.info("Mybatis page end");
		//MyBatis 需要返回一个List对象，这里只是满足MyBatis而作的临时包装
		return Collections.singletonList(pageResult);
	}

	@Override
	public Object plugin(Object target) {
		 return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		dialect = properties.getProperty("dialect");
		if(dialect == null || "".equals(dialect)) {
			throw new RuntimeException("Mybatis page plugin property[dialect] is empty");
		}
	}

}

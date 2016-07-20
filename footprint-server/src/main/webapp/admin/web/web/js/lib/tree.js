var Tree = function () {

	var maxRange = 0;
	var getTreeData = function(option, callBack) {
		var data =[
            {"dimensionCode":"msop.suning.com","dimensionName":"msop.suning.com","featureValue":2031.75,"moreDesc":""},
            {"dimensionCode":"talk8a.suning.com","dimensionName":"talk8a.suning.com","featureValue":1438.54,"moreDesc":""},
            {"dimensionCode":"passport.suning.com","dimensionName":"passport.suning.com","featureValue":907.32,"moreDesc":""},
            {"dimensionCode":"image.suning.cn","dimensionName":"image.suning.cn","featureValue":556.26,"moreDesc":""},
            {"dimensionCode":"talk8b.suning.com","dimensionName":"talk8b.suning.com","featureValue":403.33,"moreDesc":""}];
		if(option.needClear){
			$(option.container).html("");
		}
		if (data.length > 0) {
			// 有菜单
			maxRange = data[0].featureValue;
			var childNode = true;
			if(option && option.hasOwnProperty("hasChildNode")){
				childNode = option.hasChildNode;
			}
			createTreeList(option, data, childNode, callBack, true);
		} else if (data.length == 0) {
			// 没有菜单
		}
		// $.ajax({
		// 	url : option.url,
		// 	type : "post",
		// 	data : option.param,
		// 	success : function(data) {
		// 		if(option.needClear){
		// 			$(option.container).html("");
		// 		}
		// 		if (data.length > 0) {
		// 			// 有菜单 todo
		// 			maxRange = data[0].featureValue;
		// 			var childNode = true;
		// 			if(option && option.hasOwnProperty("hasChildNode")){
		// 				childNode = option.hasChildNode;
		// 			}
		// 			createTreeList(option, data, childNode, callBack, true);
		// 		} else if (data.length == 0) {
		// 			// 没有菜单 todo
		// 		}
		// 	}
		// })
	}

	var splitContent = function(obj){
		var textLength = $(obj).width();
		var content = $(obj).parents(".left").eq(0);
		var divWidth = content.width();
		if(textLength > divWidth - 18){
			var $text1 = $(obj).clone();
			var textWidth = (divWidth - 18)/2-10;
			$(obj).remove();
			$text1.width(textWidth);
			content.append($text1);
			content.append("<span class='text'>...</span>");
			var $text2 = $text1.clone();
			$text2.width(textWidth);
			content.append($text2);
			$text2.scrollLeft(textLength-textWidth/2);

		}

	}

	var createTreeList = function(option, data, hasChildMenu,callBack,isfirstMenu){
		var $ul = $("<ul></ul>"),
			downIcon = '<i class="fa fa-caret-right"></i>';

		if(!hasChildMenu){
			downIcon = '';
		}
		if( $(option.container).parents(".tree").length < 1){
			$(option.container).addClass("tree");
		}

		var childLevel = $(option.container).parents(".tree ul").length+1;

		$.each(data, function(i,v){

			var percent	= v.featureValue/maxRange*100;
			if(isNaN(percent)|| percent<0 ){
				percent=0;
			}
			if(percent>100){
				
				percent=100;
			}
			if($.trim(v.dimensionCode)!=""){
				$ul.append('<li><a href="javascript:;" title="'+v.dimensionName+"\n"+v.moreDesc+'"data-level="'+childLevel+'" data-id="'+ v.dimensionCode +'"><div class="li-content"><div class="left">'
						+ downIcon +'<span class="text">'+v.dimensionName + '</span></div><span>' + v.featureValue+option.unit+'</span>'
						+'<span class="background" style="width:'+percent+'%;"></span></div><div class="right-arrow"></div></a></li>');
			}
			

		})

		$(option.container).append($ul);

		//
		$ul.find(".text").each(function(){
			splitContent($(this));
		})

		//绑定点击菜单箭头获取二级菜单活展开二级菜单事件
		$ul.on("click"," > li > a > .li-content > .left > i", function(e){
			var childLevel = $(this).parents(".tree li").length;
			if(childLevel==option.stopLevel){
				return
			}
			e.stopPropagation();
			var $subMenu = $(this).parents("li").eq(0).find("ul");
			if($(this).hasClass("fa-caret-right")){
				$(this).removeClass("fa-caret-right").addClass("fa-caret-down");
				if($subMenu.length > 0){
					$subMenu.eq(0).slideDown(200);
				}else{
					var dimensionId = $(this).parents("a").eq(0).data("id");
					var _option = option;
					var childLevel = $(this).parents(".tree li").length;
					if(_option.seledimen=='' || _option.seledimen==undefined || _option.seledimen==null){

					}else{
						var seledimen=_option.seledimen;
						_option.param.condition =seledimen[childLevel-1]+":"+dimensionId;
						_option.param.dimension=seledimen[childLevel];
					}
					_option.container = $(this).parents("li").eq(0);

					_option.needClear = false;
					getTreeData(_option,callBack);

				}
			}else if($(this).hasClass("fa-caret-down")){
				$subMenu.eq(0).slideUp(200);
				$(this).removeClass("fa-caret-down").addClass("fa-caret-right");
			}
		});

		//绑定点击事件
		$ul.on("click"," > li > a ", function(){
			var childLevel = $(this).parents(".tree li").length;
			if($(this).hasClass("active")){
				return;
			}
			$(".tree").find("a").removeClass("active");
			$(".right-arrow").hide();
			$(this).addClass("active");
			$(this).find(".right-arrow").eq(0).show();

			var dimensionId = $(this).data("id");
			callBack(dimensionId, childLevel);

		});
	}

	return {

		init: function(option, callBack){
			$(option.container).html("");
			option.needClear=true;
			getTreeData(option,callBack);
		},

		createTreeFirstList: function(option, callback){
			$(option.container).html("");
			option.needClear=true;
			maxRange = option.data[0].featureValue;
			createTreeList(option, option.data, option.hasChildNode, callback, true);
		}
	};
}();
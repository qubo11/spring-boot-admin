var Plugins = function() {
	
	/**
	 * DateTime插件，初始化年月日，不加时分秒
	 */
	var initMonthDate = function(dateId,changeFunc) {
		$(dateId).datetimepicker({
			language:"zh-CN",
            format: 'yyyy-mm-dd',
            autoclose:true,
            maxView:"decade",
            todayBtn: true,
            minView:"month"
        }).on('changeDate', function(ev){
        	if(changeFunc){
        		changeFunc(ev);
        	}
        });
	};
	
	/**
	 * DateTime插件，初始化年月日时分秒
	 */
	var initHourDate = function(dateId,changeFunc){
		$(dateId).datetimepicker({
			language:"zh-CN",
            format: 'yyyy-mm-dd hh:ii',
            autoclose:true,
            maxView:"decade",
            todayBtn: true,
            minView:"hour"
        }).on('changeDate', function(ev){
        	if(changeFunc){
        		changeFunc(ev);
        	}
        });
	}

	return {
		initMonthDate : function(dateId,changeFunc){
			initMonthDate(dateId,changeFunc);
		},
		initHourDate : function(dateId,changeFunc){
			initHourDate(dateId,changeFunc);
		}
	}
}();

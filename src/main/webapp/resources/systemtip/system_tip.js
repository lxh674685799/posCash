var system_tip={
		id:'',
		path:'',
        level:'',//warning,info ,error
		interval:120000,
		tip_setting:{
				id:'',
				width:120,
				height:30,
				title:'系统提醒',
				tip:'系统提醒',
				position:'',
				img:'styles/default/images/onlinegroup_mini.gif',
				url:'',
				contentUrl:'',
				widgetDefault :{
					tipable:true,
					intervalable:false
				}
		}
};
var interval_array=[];
var objArray=[];
system_tip.add=function(arry){
	if(jQuery.isArray(arry)){
		objArray=arry;
		for(var i=0; i<arry.length;i++){
			var obj=jQuery.extend({},this.tip_setting,arry[i]);
			this.addTip(obj);
			//加载并添加信息
			this.loadInfo(obj);
			//添加定时请求
			if(obj.widgetDefault.intervalable&&obj.interval!==0){
				interval_array.push(obj);
			}
		}
	}else{
		objArray.push(arry);
		this.addTip(arry);
		//加载并添加信息
		this.loadInfo(arry);
		//添加定时请求
		if(arry.widgetDefault.intervalable&&arry.interval!==0){
			interval_array.push(arry);
		}
	}
	//添加动作
	this.addEvent();
	//添加定时请求
	if(interval_array.length!==0){
		setInterval(function(){
			for(var i=0;i<interval_array.length;i++){
				system_tip.loadInfo(interval_array[i]);
			}
		},system_tip.interval);
	}
};
system_tip.addFunctionTip=function(){
	var id = this.id;
	//如果加载的信息都为0，则不显示
	if(jQuery('#div_functionTip').length!==0)return;
	this.addTip({id:'functionTip',width:40,height:30,img:'styles/default/images/onlinegroup_mini.gif'});
	jQuery('img','#div_functionTip').replaceWith("<span>隐藏</span>");
	//添加样式,title和动作
	jQuery('#div_functionTip')
	.attr('title','隐藏系统提醒')
	.toggle(function(){
		this.title='显示系统提醒';
		jQuery(id+'>div').not('#div_functionTip').hide();
		jQuery(this).find('span').html("显示");
	},function(){
		this.title='隐藏系统提醒';
		jQuery(id+'>div').not('#div_functionTip').show();
		jQuery(this).find('span').html("隐藏");
	})
	.animate({ opacity: '0.5'},"normal");
};
system_tip.addTip=function(obj){
	//检测是否有tip，如果有则依次向左显示
	rightPx=50;
	/*if(jQuery(id).length==0){
		jQuery(document.body).append('<div id="system_tip" style="width:100%;height:30px;bottom:0px;right:0px;position:absolute;background-color: green;"></div>');
	}*/
	if(obj.id=='functionTip'){
		rightPx=10;
	}else{
		if(jQuery(this.id+'>div:last').length!=0){
			var ele = jQuery(this.id+'>div:last');
			rightPx=parseInt(ele.css('right'))+ele.width();
		}
	}
	    var ele =['<div id="div_', obj.id,'" class="tip"',
		'style="width:',obj.width,'px;height: ',obj.height,'px;right: ',
		rightPx,'px;">'];
	    if(obj.img){
	    	ele.push('<div class="panelbarbutton">',
	    			'<img id="img_',obj.id,'" width="" height="" class="icon"/>'
	    			);
	    }
	    ele.push('</div></div>');
	    
		jQuery(this.id).append(ele.join(''));
};
system_tip.init=function(id,path){
	this.id='#'+id;
		this.path=path;
	/*	var href = path+'/resources/script/systemtip/system_tip.css';
		jQuery('head').append('<link href="' + href + '" rel="stylesheet" type="text/css" />');*/
    //ajax 全局配置
    jQuery(document).ajaxStop(function(evt, request, settings) {
             //添加显示，隐藏系统提醒功能
			  system_tip.addFunctionTip();
				jQuery('div[block=true]','#'+id).each(function(i){
                    var width =jQuery(this).width();
                     jQuery(this).nextAll('div:not(#div_functionTip)').each(function(i){
					 var right = $(this).css('right').substr(0,$(this).css('right').length-2);
					 var newright=parseInt(right)-parseInt(width);
					 $(this).css('right',newright);
				    });
					 $(this).remove();
                    });
              //删除没有提示信息的tip并将其后面的tip的right右移
                    var divs=jQuery('#'+id+'>div');
                    if(divs.not('#div_functionTip').length==0){
                       divs.remove();
                       return ;
                    };
        });
};

system_tip.loadInfo=function(obj){
		if(obj.url==='')return;
		//添加提示
		if(obj.widgetDefault.tipable){
			jQuery('#div_'+obj.id).attr('title',obj.tip);
		}
		jQuery.getJSON(obj.url+"?curTTTTtimestamp="+new Date().getTime(),function(data){
			if(data.count!==0){
			//添加标题信息(先清除)
			var countEle =jQuery('#count_'+obj.id);
			if(countEle.length!==0){
				countEle.html(data.count)
			}else{
				jQuery('#img_'+obj.id).after([obj.title,'<span id="count_',obj.id,'">',data.count,'</span>'].join(''));
			}
                var level ="info";
                if(obj.level){
                    level= obj.level;
                }
                jQuery('#count_'+obj.id).addClass("badge").addClass("badge-"+level);
				var winEle=jQuery('.window','#div_'+obj.id);
				if(winEle.length!==0){
					var liList=[];
					jQuery.each(data.list,function(i,content){
                        var con='<span class="label label-info">'+(i+1)+'</span>'+content.title;
                        if(obj.id=='messageTip'){
							liList.push('<li id="li_',obj.id,'_',content.id,'" class="ellipsis"><a href="javascript:;" onclick="showReadMsgDlg(\'',content.id,'\');" title="',content.title,'">',con,'</a></li>');
						}					
					});
					liList.push('<li style="text-align:right" class="ellipsis"><a style="color:red;" href="',obj.contentUrl,'">更多>></a></li>');
					winEle.find('ul').html(liList.join(''));
				}else{
					//添加弹出框
					jQuery('#div_'+obj.id)
					.append(
							(function(){
								var liList = ['<div class="window"><div><h4>',obj.title,'</h4><menu></menu></div><ul>'];
								jQuery.each(data.list,function(i,content){
									var con='<span class="label label-info">'+(i+1)+'</span>'+content.title;
									if(obj.id=='messageTip'){
										liList.push('<li id="li_',obj.id,'_',content.id,'" class="ellipsis"><a href="javascript:;" onclick="showReadMsgDlg(\'',content.id,'\');" title="',content.title,'">',con,'</a></li>');
									}
								});
								liList.push('<li style="text-align:right" class="ellipsis"><a style="color:red;" href="',obj.contentUrl,'">更多>></a></li>');
								liList.push('</ul></div>');
								return liList.join('');
							})
					);
				}
			if(jQuery('.minimize','#div_'+obj.id+' menu').length===0){
				//添加最小化组件
				jQuery('#div_'+obj.id+' menu').
				append(
					jQuery('<li class="minimize" label="最小化" title="最小化"></li>').click(function(){
						$(this).parents('.window').removeClass('actived').prev().removeClass('activedDiv');
				})
				);
			}
			}else{
                //添加“空”标志 (ajax 请求后会把有“空”标志的tip删除掉)
                jQuery('#div_'+obj.id).attr('block','true');
			}
		}
		);
};
system_tip.addEvent=function(){
	jQuery('.panelbarbutton').each(function(){
		    var tempObj = jQuery(this);
		    	if(tempObj.parent().attr('id')==='div_functionTip')return;
			    tempObj.mouseover(function (e) {
                      tempObj.css({'background-color':'#fff'});
                	 }).mouseout(function (e) {
                		 if(!tempObj.next().hasClass('actived')){
                			 tempObj.css({'background-color':'#E5E5E5'});
                		 }
                		 return false;
                	 }).mousedown(function (e) {
			          jQuery('.panelbarbutton').css({'background-color':'#E5E5E5'});
                      tempObj.css({'background-color':'#fff'});
                      return false;
                	 }).click(function(){
	                		 jQuery('.activedDiv').not(tempObj).removeClass('activedDiv');
	                 		 jQuery('.actived').not(tempObj.next()).removeClass('actived');
                		     if(tempObj.next().is('div')){
                		    	 tempObj.next().toggleClass('actived'); 
                         		 tempObj.toggleClass('activedDiv'); 
                		     }
                     		 
                	 });
		});
};
system_tip.updateTip=function(ids,type){
	var id = this.id;
	if(jQuery('#div_'+type).length==0)return;
	if(jQuery.isArray(ids)){
		jQuery.each(ids,function(i,id){
			if(jQuery('#li_'+type+'_'+id).length!==0){
				jQuery('#li_'+type+'_'+id).remove();
				var countEle=jQuery('#count_'+type);
				countEle.html(parseInt(countEle.html())-1);
			}
			
		});
	}else{
		if(jQuery('#li_'+type+'_'+ids).length!==0){
			jQuery('#li_'+type+'_'+ids).remove();
			var countEle=jQuery('#count_'+type);
			countEle.html(parseInt(countEle.html())-1);
		}
	}
	//判断是否还有未读信息，如果没有则删除弹出窗口
	var infoEle =jQuery('.window ul li','#div_'+type);
	if(infoEle.length===1){
		jQuery('.window','#div_'+type).remove();
		var nextEle=jQuery('#div_'+type).nextAll('div:not(#div_functionTip)');
		var width=jQuery('#div_'+type).width();
		jQuery('#div_'+type).remove();
		nextEle.each(function(i){
			var right = $(this).css('right').substr(0,$(this).css('right').length-2);
		    var newright=parseInt(right)-parseInt(width);
		    $(this).css('right',newright);
		});
		if(jQuery(id+'>div').not('#div_functionTip').length===0){
			jQuery('#div_functionTip').remove();
		}
	}
	jQuery('.panelbarbutton','#div_'+type).removeClass('activedDiv');
	
}
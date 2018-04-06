function graphTrace(options) {

    var _defaults = {
        srcEle: this,
        pid: options
    };
    var opts = $.extend(true, _defaults);
    
    
    
    //alert(options);
    // 获取图片资源
   var imageUrl = ctx+"/workFlowProcessController.ftl?processInstance&pid=" + opts.pid + "&type=image"+"&timetemp="+new Date();
   var jsonUrl = ctx+'/workFlowProcessController.ftl?processTrace&pid=' + opts.pid+"&timetemp="+new Date();
    $.getJSON(jsonUrl , function(infos) {
    
        var positionHtml = "";
        
        // 生成图片
        var varsArray = new Array();
        $.each(infos, function(i, v) {
            var $positionDiv = $('<div/>', {
                'class': 'activiyAttr'
            }).css({
                position: 'absolute',
                left: (v.x - 1),
                top: (v.y - 1),
                width: (v.width - 2),
                height: (v.height - 2)
            })
            if (v.currentActiviti) {
                $positionDiv.addClass('ui-corner-all-12').css({
                    border: '2px solid red'
                });
            }
            positionHtml += $positionDiv.outerHTML();
            varsArray[varsArray.length] = v.vars;
        });
        
        if ($('#workflowTraceDialog').length == 0) {
            $('<div/>', {
                id: 'workflowTraceDialog',
                title: "查看流程（按ESC键可以关闭）",
                html: "<div><img src='" + imageUrl + "' style='position:absolute; left:0px; top:0px;' />" +
                "<div id='processImageBorder'>" +
                positionHtml +
                "</div>" +
                "</div>"
            }).appendTo('body');
        } else {
            $('#workflowTraceDialog img').attr('src', imageUrl);
            $('#workflowTraceDialog #processImageBorder').html(positionHtml);
        }
        
        // 设置每个节点的data
        $('#workflowTraceDialog .activiyAttr').each(function(i, v) {
            $(this).data('vars', varsArray[i]);
        });
        
        // 打开对话框
        $('#workflowTraceDialog').dialog({
            modal: true,
            resizable: false,
            dragable: false,
            open: function() {
                $('#workflowTraceDialog').css('padding', '0.2em');
                $('#workflowTraceDialog .ui-accordion-content').css('padding', '0.2em').height($('#workflowTraceDialog').height() - 75);
                
                // 此处用于显示每个节点的信息，如果不需要可以删除
                $('.activiyAttr').qtip({
                    content: function() {
                        var vars = $(this).data('vars');
                        var tipContent = "<table class='need-border'>";
                        $.each(vars, function(varKey, varValue) {
                            if (varValue) {
                                tipContent += "<tr><td class='label'>" + varKey + "</td><td>" + varValue + "<td/></tr>";
                            }
                        });
                        tipContent += "</table>";
                        return tipContent;
                    },
                    position: {
                        at: 'bottom left',
                        adjust: {
                            x: 3
                        }
                    }
                });
                // end qtip
            },
            width: document.documentElement.clientWidth * 0.9,
            height: document.documentElement.clientHeight * 0.9
        });
        
    });
}

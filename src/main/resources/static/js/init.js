function init(){

    //弹出检测人框
    $('a[name=showPeople]').click(function(e){
        if($(this).is('.isShow')){
            $('div[name=people_list]').slideUp();
            $(this).removeClass('isShow');
        }else{
            //获得检测人信息，画出检测人框
            GetCartPatient($(this));
            $(this).next().slideDown();
            $(this).addClass('isShow');
        }
    });
    //点击添加已有检测人或者添加新的检测人
    $("div[name=add]").click(function(e){

        if ($(this).attr('id')=="addPatient") {
            //弹出已保存的人名单，无保存的填写信息


            //获取当前购物车项目信息
            var cartItem = $(this).parents('div.weui-panel').find('input[name=cart_info]').data();

            var patientInfo = GetPatientInfo(cartItem);


        }else{

            //弹出全屏的信息填写框
            PatientInfoConfirm();
            $("#pop_write").popup();
        }

    });
}

//点击数量框减少按钮
/*$('div[name=decrement]').click(function Decrement(e){
    var priceNode =  $(this).parent().parent().prev().find("p");
    var countNode = $(this).next().children();
    if (parseInt(countNode.val())>1) {


        countNode.val(parseInt(countNode.val())-1);
        $.ajax({

        });
        priceNode.text("金额：￥"+(unit_price*countNode.val()));
    }else{
        $.confirm({
            title: '确认删除',
            text: '确认删除该订单吗？',
            onOK: function () {

            $.ajax({

            });
            priceNode.parent().parent().remove();
        },
        onCancel: function () {
              //点击取消

          }
        });

    }
})*/



//点击数量框增加按钮
/*$('div[name=increment]').click(function Increment(e){
    var priceNode =  $(this).parent().parent().prev().find("p");
    var countNode = $(this).prev().children();
    if (countNode.val()<9) {
        var unit_price = GetUnitPrice($(this).parent().parent().prev().find("h4").text());
        countNode.val(parseInt(countNode.val())+1);



        priceNode.text("金额：￥"+(unit_price*countNode.val()));
    }

});*/
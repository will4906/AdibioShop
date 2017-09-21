//画出购物车
function DrawCart() {
    $('#content').empty();
    var cartTemplet;
    if (cartData.result == "ok") {
        for (var i = 0; i < cartData.parm.length; i++) {
            unit_price[cartData.parm[i].product_name] = cartData.parm[i].unit_price;
            $('#cartItem').find('span[name=productName]').html(cartData.parm[i].product_name);
            $('#cartItem').find('span[name=money]').html(cartData.parm[i].unit_price * cartData.parm[i].quantity);
            cartTemplet = $('#cartItem').clone();
            //html += cartTemplet;
            $('#content').append(cartTemplet);
        }
        BindCartInfo();
    } else {
        alert('获取购物车信息失败');
    }
}
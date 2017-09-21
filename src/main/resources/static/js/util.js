

function setTimer(time,statusNode,listNode){
    if (time == 0) {
        listNode.remove();

        return 0;
    }else{
        statusNode.text('支付剩余时间：'+parseInt(time/60)+':'+((time%60<10?('0'):'')+time%60));
        setTimeout(function () {
            setTimer(time-1,statusNode,listNode);
        },1000);
    }
}
function replyU(event, replyId){
    const replyTr=document.querySelector('.r'+replyId);
    const replyRead=replyTr.querySelector('.read');
    const replyUpdate=replyTr.querySelector('.update');
    replyRead.style.display='none';
    replyUpdate.style.display='table-cell';
}

function replyUx(event, replyId){
    const replyTr=document.querySelector('.r'+replyId);
    const replyRead=replyTr.querySelector('.read');
    const replyUpdate=replyTr.querySelector('.update');
    replyRead.style.display='table-cell';
    replyUpdate.style.display='none';
}
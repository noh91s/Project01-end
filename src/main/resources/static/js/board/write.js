const replyWriteBtn=document.querySelector('#replyWriteBtn');
const replyXBtn=document.querySelector('#replyXBtn');
const replyWriteForm=document.querySelector('.reply-write');


replyWriteBtn.addEventListener('click',writeFn);
replyXBtn.addEventListener('click',xFn);

function writeFn(event){
    searchBack.style.display='flex';
    replyWriteForm.style.display='flex';
}

function xFn(event){
    searchBack.style.display='none';
    replyWriteForm.style.display='none';
}
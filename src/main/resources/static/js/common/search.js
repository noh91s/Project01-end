const searchBtn=document.querySelector('#searchBtn');
const xBtn=document.querySelector('#xBtn');
const searchBack=document.querySelector('.searchBack');
const searchForm=document.querySelector('#searchForm');

searchBtn.addEventListener('click',searchFn);
xBtn.addEventListener('click',xFn);

function searchFn(event){
    searchBack.style.display='flex';
    searchForm.children[0].style.display='flex';
}

function xFn(event){
    searchBack.style.display='none';
    searchForm.children[0].style.display='none';
}
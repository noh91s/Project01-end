//csrf token
const token = $("meta[name='_csrf']").attr("content")
const header = $("meta[name='_csrf_header']").attr("content");
const name = $("#userName").val();

$('#replyBtn').on('click', replyFn);

function replyFn(){

    searchBack.style.display='none';
    replyWriteForm.style.display='none';

const data={
            'boardId':$('#boardId').val(),
            'email':$('#email').val(),
            'replyContent':$('#replyContent').val(),
            'replyWriter':$('#replyWriter').val(),
            };

    $.ajax({
        type:"POST",
        url:"/reply/writeBtn",
        beforeSend : function(xhr) {
                    xhr.setRequestHeader(header, token);
                },
        data: data,
        success:function(res){
//          alert(res);
          console.log(res);

//          let reData="<tr>";
//          reData+= "<td>"+res.boardId+"</td>";
//          reData+= "<td>"+res.memberId+"</td>";
//          reData+= "<td>"+res.id+"</td>";
//          reData+= "<td>"+res.replyContent+"</td>";
//          reData+= "<td>"+res.replyWriter+"</td>";
//          reData+= "<td>"+res.createTime+"</td>";
//          reData+="</tr>";
//          $('#tData').append(reData);

          let cTime = moment(res.createTime).format("YYYY-MM-DD HH:mm:ss");
          let reData ="<tr><td>";
          reData+= "<span class='span1'>"+res.replyWriter+"</span><br>";
          reData+= "<span class='span2'>"+cTime+"</span><br>";
          reData+= "<span class='span3'>"+res.replyContent+"</span><br>";
//          reData+= "<span class='span4'><span><a href=/reply/update/"+res.id+">"+"수정"+"</a></span>";
//          reData+= "<span><a href=/reply/delete/"+res.id+">"+"삭제"+"</a></span></span>";
          reData+= "<div class='reply-list-divide'><hr class='divide'></div>";
          reData+= "</td></tr>";
          $('#replyData').append(reData);

        },
        error:()=>{
            alert("오류")
        }
    });

}
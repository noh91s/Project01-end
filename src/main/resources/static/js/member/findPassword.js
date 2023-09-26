
//csrf token
const token = $("meta[name='_csrf']").attr("content")
const header = $("meta[name='_csrf_header']").attr("content");
const name = $("#userName").val();

var csrfToken = $("#csrfToken").val();

$('#submitBtn').on('click', findFn);

function findFn() {

    var email = $('#email').val();
    var nickName = $('#nickName').val();

    var formData = {
                    email: email,
                    nickName: nickName
                };
    // AJAX 요청을 보내는 부분
    $.ajax({
        contentType: 'application/json',
        type: 'POST', // 또는 'GET' 등 적절한 HTTP 메서드를 선택하세요.
        url: '/mail/password', // 서버 엔드포인트 URL을 설정하세요.
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        }, // 쉼표 추가
        data: JSON.stringify(formData),
            success: function (data) {
                flag = false;
                // 성공적인 응답을 처리하세요.
                console.log('성공');
                console.log(data);

                if(data == 'fail'){
                    alert("일치하지 않는 회원 정보입니다.");
                }else if(data == 'ok'){
                    alert("메일이 발송되었습니다");
                    window.location.href = '/member/login';
                }
            }

    });
};
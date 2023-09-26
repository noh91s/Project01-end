const token = $("meta[name='_csrf']").attr("content")
const header = $("meta[name='_csrf_header']").attr("content");
const name = $("#userName").val();

var flag = false;
var code = "";

$(document).ready(function() {
    let emailInput = document.querySelector("#email");
    let passwordInput = document.querySelector("#password");
    let emailCodeBtnInput = document.querySelector("#emailCodeBtn");
    let nickNameInput = document.querySelector("#nickName");

    $('#submit').on('click', function (e) {
        if (
            nickNameInput.value.trim() === "" ||
            emailInput.value.trim() === "" ||
            passwordInput.value.trim() === "" ||
            emailCodeBtnInput.value.trim() === ""
        ) {
            e.preventDefault();
            alert("필수 정보를 모두 입력해주세요.");
        }
    });
});

// 초기에 submit 버튼 비활성화
$("#submit").prop("disabled", true);

// 회원가입 버튼 클릭 이벤트 핸들러 등록
$('#submit').on('click', function () {
    if (!isEmailVerified()) {
        alert("이메일 인증을 먼저 완료하세요.");
        $("#submit").prop("disabled", true); // 인증 실패 시 submit 버튼 비활성화
        return; // 이메일 인증이 되지 않은 경우 회원가입 중지
    }

    // 이메일 인증이 완료되었을 때만 회원가입 로직을 실행
    var email = $('#email').val();
    // 다음 회원가입 로직을 진행...
});

// 이메일 인증 확인 함수
function isEmailVerified() {
    var enteredCode = $('#emailCode').val(); // 사용자가 입력한 코드
    return enteredCode === code; // 인증 코드가 일치하는지 여부를 반환
}


const emailCheck = (event)=>{

    console.log(event);

    const email = document.getElementById("email").value;
    const checkResult = document.getElementById("check-result");
    console.log("입력값: " , email);
    $.ajax({
        // 요청방식: post, url: "emailCheck", 데이터: email
        type: "post",
        url: "/member/emailCheck",
        beforeSend : function(xhr) {
                xhr.setRequestHeader(header, token);
            },
        data:{
            "email" : email
        },
        success: function(res){
            console.log("요청성공", res);
            if (res == "ok") {
                console.log("사용가능한 이메일입니다.");
                checkResult.innerHTML = "사용가능한 이메일입니다.";
                $("#check-result").css("color", "green");


                // 이메일 체크 버튼 클릭 이벤트 핸들러 등록
                $('#emailBtn').on('click', function () {
                    if (flag) {
                        alert("실행중입니다. 잠시만 기다려 주세요.");
                        return;
                    }

                    // 사용자가 이메일을 입력한 경우에만 실행
                    var email = $('#email').val();
                    if (email.trim() === "") {
                        alert("이메일을 입력하세요.");
                        return;
                    }

                    flag = true;

                    // AJAX 요청을 보내는 부분
                    $.ajax({
                        contentType: 'application/json',
                        type: 'POST',
                        url: '/mail/emailCheck',
                        beforeSend : function(xhr) {
                            xhr.setRequestHeader(header, token);
                        },
                        data: JSON.stringify({ email: email }),
                        success: function (data) {
                            flag = false;
                            console.log('성공');
                            code = data;
                            if (data === null) {
                                alert("일치하지 않는 회원 정보입니다.");
                                $("#submit").prop("disabled", true); // 이메일 검증 실패 시 submit 버튼 비활성화
                            } else if (data === code) {
                                alert("메일이 발송되었습니다");
                                $("#submit").prop("disabled", false); // 이메일 검증 성공 시 submit 버튼 활성화

                                // 여기서 이벤트 핸들러를 등록할 수 있습니다.
                            }
                        },
                        error: function (xhr, status, error) {
                            flag = false;
                            console.log('실패');
                            console.log(xhr.responseText);
                            var statusCode = xhr.status;
                            alert(xhr.responseText);
                            console.log('오류 상태 코드: ' + statusCode);
                            $("#submit").prop("disabled", true); // 서버 오류 시 submit 버튼 비활성화
                        }
                    });
                });

                // 이메일 코드 확인 버튼 클릭 이벤트 핸들러 등록
                $('#emailCodeBtn').on('click', function () {
                    var enteredCode = $('#emailCode').val(); // 사용자가 입력한 코드
                    console.log(code);

                    if (enteredCode === code) {
                        alert("인증이 완료되었습니다.");
                        $("#submit").prop("disabled", false); // 인증 완료 시 submit 버튼 활성화
                    } else {
                        alert("인증 코드가 일치하지 않습니다.");
                        $("#submit").prop("disabled", true); // 인증 실패 시 submit 버튼 비활성화
                    }
                });

            } else {
                console.log("이미 사용중인 이메일입니다.");
                checkResult.innerHTML = "이미 사용중인 이메일입니다. 다른 이메일을 입력하세요.";
                $("#check-result").css("color", "red");
                $("#submit").attr("disabled", "disabled");
            }
        },
        error: function(res){
            console.log("에러발생", res);
        }
    })
}
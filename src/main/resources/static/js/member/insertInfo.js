$(document).ready(function() {
    let nickNameInput = document.querySelector("#nickName");

    $('#submit').on('click', function (e) {
        if (nickNameInput.value.trim() === "") {
            e.preventDefault(); // 폼 제출을 막음
            alert("닉네임을 입력해주세요.");
        }
    });
});

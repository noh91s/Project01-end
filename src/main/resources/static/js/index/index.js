document.addEventListener("DOMContentLoaded", function () {

    // 첫 번째 슬라이더 초기화 (4씩 이동)
    initializeSlider("#slider", "#preBtn", "#nextBtn", 110, 4);
    // 두 번째 슬라이더 초기화 (1씩 이동)
    initializeSlider("#sliderMovie", "#sliderMovie_preBtn", "#sliderMovie_nextBtn", 310, 1);

    function initializeSlider(sliderId, preBtnId, nextBtnId, slideWidth, step) {
        const slider = document.querySelector(sliderId);
        const preBtn = document.querySelector(preBtnId);
        const nextBtn = document.querySelector(nextBtnId);
        const visibleSlides = 4;
        let currentIndex = 0;

        nextBtn.addEventListener("click", function () {
            if (currentIndex < slider.children.length - visibleSlides) {
                currentIndex += step;
                updateSlider();
            }
            updateButtonState();
        });

        preBtn.addEventListener("click", function () {
            if (currentIndex > 0) {
                currentIndex -= step;
                updateSlider();
            }
            updateButtonState();
        });

        function updateSlider() {
            const translateX = -currentIndex * slideWidth;
            slider.style.transform = `translateX(${translateX}px)`;
        }

        function updateButtonState() {
            preBtn.style.display = currentIndex === 0 ? "none" : "block";
            nextBtn.style.display = currentIndex >= slider.children.length - visibleSlides  ? "none" : "block";
        }

        updateButtonState();
    }


});


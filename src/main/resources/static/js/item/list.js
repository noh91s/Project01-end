document.addEventListener("DOMContentLoaded", function () {

    const slider = document.querySelector("#slider");
    const preBtn = document.querySelector("#preBtn");
    const nextBtn = document.querySelector("#nextBtn");
    const slideWidth = 430;
    const visibleSlides = 4;
    let currentIndex = 0;

    nextBtn.addEventListener("click", function () {
        if (currentIndex < slider.children.length - visibleSlides) {
            currentIndex++;
            updateSlider();
        }
        updateButtonState();
    });

    preBtn.addEventListener("click", function () {
        if (currentIndex > 0) {
            currentIndex--;
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
        nextBtn.style.display = currentIndex + 1 >= slider.children.length - visibleSlides ? "none" : "block";
    }

    updateButtonState();
});
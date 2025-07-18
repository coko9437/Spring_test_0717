document.addEventListener("DOMContentLoaded", function () {
    const weatherBtn = document.getElementById("weatherBtn");
    const weatherModal = document.getElementById("weatherModal");
    const closeBtn = document.getElementById("closeBtn");
    const temperatureEl = document.getElementById("temperature");
    const descriptionEl = document.getElementById("description");

    // 버튼 클릭 시 실행
    weatherBtn.addEventListener("click", function () {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function (position) {
                const lat = position.coords.latitude;
                const lon = position.coords.longitude;

                fetch(`/weather?lat=${lat}&lon=${lon}`)
                    .then(response => response.json())
                    .then(data => {
                        temperatureEl.textContent = `온도: ${data.temperature}°C`;
                        descriptionEl.textContent = `상태: ${data.description}`;
                        weatherModal.style.display = "block";
                    })
                    .catch(error => {
                        alert("날씨 정보를 불러올 수 없습니다.");
                        console.error("Error:", error);
                    });
            });
        } else {
            alert("이 브라우저에서는 위치 정보 사용이 불가능합니다.");
        }
    });

    // 닫기 버튼 클릭 시 모달 숨김
    closeBtn.addEventListener("click", function () {
        weatherModal.style.display = "none";
    });

    // 바깥 클릭 시 모달 닫기
    window.addEventListener("click", function (event) {
        if (event.target === weatherModal) {
            weatherModal.style.display = "none";
        }
    });
});
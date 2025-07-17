document.addEventListener('DOMContentLoaded', function () {
    const weatherBtn = document.getElementById("weatherBtn");

    if (weatherBtn) {
        weatherBtn.addEventListener("click", function () {
            axios.get('/weather', {
                params: {
                    lat: 37.57,
                    lon: 126.98
                }
            })
                .then(response => {
                    const data = response.data;
                    const modalBody = document.getElementById("weatherModalBody");

                    modalBody.innerHTML = `
                        <ul>
                            <li>날짜: ${data.date}</li>
                            <li>지역: ${data.region}</li>
                            <li>기온: ${data.temperature}°C</li>
                            <li>날씨: ${data.description}</li>
                        </ul>
                    `;

                    const weatherModal = new bootstrap.Modal(document.getElementById("weatherModal"));
                    weatherModal.show();
                })
                .catch(error => {
                    console.error("날씨 정보를 불러오지 못했습니다.", error);
                    alert("날씨 정보를 가져오는 데 실패했습니다.");
                });
        });
    }
});
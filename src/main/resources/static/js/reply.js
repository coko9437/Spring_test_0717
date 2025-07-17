async function fetchWeather(lat, lon) {
    try {
        const res = await axios.get(`/weather?lat=${lat}&lon=${lon}`)
        return res.data
    } catch (err) {
        console.error("날씨 API 오류:", err)
        return null
    }
}

document.addEventListener("DOMContentLoaded", () => {
    const btn = document.querySelector("#weatherBtn")

    if (btn) {
        btn.addEventListener("click", async (e) => {
            e.preventDefault()

            // 서울 좌표 (또는 추후 geolocation API 활용 가능)
            const lat = 37.5665
            const lon = 126.9780

            const data = await fetchWeather(lat, lon)

            const weatherDiv = document.querySelector("#weatherInfo")
            if (data) {
                weatherDiv.innerHTML = `
                    <div class="alert alert-info">
                        <strong>기온:</strong> ${data.temperature} °C <br>
                        <strong>날씨:</strong> ${data.description}
                    </div>
                `
            } else {
                weatherDiv.innerHTML = `<div class="alert alert-danger">날씨 정보를 불러오지 못했습니다.</div>`
            }
        })
    }
})
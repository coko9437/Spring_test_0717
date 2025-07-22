// 페이지가 로드되면 자동 호출 (필요에 따라 버튼 클릭 시로 변경 가능)
document.addEventListener('DOMContentLoaded', function () {
    fetchPublicData(1, 10); // pageNo=1, numOfRows=10
});

function fetchPublicData(pageNo, numOfRows) {
    const url = `/api/publicdata?pageNo=${pageNo}&numOfRows=${numOfRows}`;

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP 오류: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            console.log("✅ 공공데이터 응답:", data);

            const items = data.response.body.items.item;
            displayData(items);
        })
        .catch(error => {
            console.error("🚨 데이터 호출 실패:", error);
        });
}

function displayData(items) {
    const container = document.getElementById('data-container');
    container.innerHTML = ""; // 기존 내용 초기화

    if (!items || items.length === 0) {
        container.innerHTML = "<p>데이터가 없습니다.</p>";
        return;
    }

    items.forEach(item => {
        const div = document.createElement('div');
        div.className = 'restaurant-item';
        div.innerHTML = `
            <h4>${item.bsnsNm}</h4>
            <p><strong>주소:</strong> ${item.addrRoad || item.addrJibun}</p>
            <p><strong>전화번호:</strong> ${item.tel || '없음'}</p>
            <p><strong>메뉴:</strong> ${item.menu}</p>
            <p><strong>업종:</strong> ${item.bsnsSector} / ${item.bsnsCond}</p>
        `;
        container.appendChild(div);
    });
}
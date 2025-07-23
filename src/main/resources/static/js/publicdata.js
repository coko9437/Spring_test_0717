function loadData(pageNo = 1, numOfRows = 10) {
    fetch(`/public-data?pageNo=${pageNo}&numOfRows=${numOfRows}`)
        .then(response => response.json())
        .then(data => {
            const listElement = document.getElementById("restaurantList");
            listElement.innerHTML = ""; // 기존 내용 비우기

            data.items.forEach(item => {
                const li = document.createElement("li");
                li.innerText = `[${item.rdnwhlAddr}] ${item.bplcNm} - ${item.foodMenu}`;
                listElement.appendChild(li);
            });

            console.log("총 데이터 수:", data.totalCount);
        })
        .catch(error => {
            console.error("데이터 로딩 실패:", error);
        });
}
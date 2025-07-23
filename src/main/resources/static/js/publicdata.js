document.getElementById('loadBtn').onclick = function() {
    loadXMLData(1, 10);
};

function loadXMLData(pageNo, numOfRows) {
    console.log('loadXMLData 함수 실행됨');
    const url = `/public-data?pageNo=${pageNo}&numOfRows=${numOfRows}`;

    fetch(url, {
        method: 'GET',
        headers: {
            'Accept': 'application/xml'
        }
    })
        .then(response => {
            console.log('응답 상태:', response.status);
            if (!response.ok) {
                throw new Error('네트워크 응답이 정상이 아닙니다');
            }
            return response.text();  // XML을 텍스트로 받음
        })
        .then(str => {
            console.log('받은 응답 문자열:', str.substring(0, 200) + '...');
            // XML 파싱
            const parser = new DOMParser();
            const xmlDoc = parser.parseFromString(str, "application/xml");

            // 파싱 오류 체크
            if (xmlDoc.getElementsByTagName("parsererror").length > 0) {
                throw new Error('XML 파싱 중 오류 발생');
            }

            // 아이템 리스트 추출
            const items = xmlDoc.getElementsByTagName('item');

            if (items.length === 0) {
                document.getElementById('result').innerHTML = '데이터가 없습니다.';
                return;
            }

            // 결과 HTML 생성
            let html = '<table border="1" cellpadding="5" cellspacing="0">';
            html += '<thead><tr>'
                + '<th>업종</th><th>업태</th><th>상호명</th><th>도로명 주소</th><th>전화번호</th><th>메뉴</th>'
                + '</tr></thead><tbody>';

            for (let i = 0; i < items.length; i++) {
                const item = items[i];

                // 태그값 추출 함수
                const getText = (tagName) => {
                    const el = item.getElementsByTagName(tagName)[0];
                    return el ? el.textContent : '';
                };

                html += '<tr>'
                    + `<td>${getText('bsnsSector')}</td>`
                    + `<td>${getText('bsnsCond')}</td>`
                    + `<td>${getText('bsnsNm')}</td>`
                    + `<td>${getText('addrRoad')}</td>`
                    + `<td>${getText('tel')}</td>`
                    + `<td>${getText('menu')}</td>`
                    + '</tr>';
            }
            html += '</tbody></table>';

            document.getElementById('result').innerHTML = html;
        })
        .catch(error => {
            console.error('XML 데이터 로드 실패:', error);
            document.getElementById('result').innerHTML = '데이터를 불러오는 데 실패했습니다.';
        });
}
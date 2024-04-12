
import { useEffect, useRef, useState } from 'react';
import icon from '../icon.png';

const makeTemplate = (element) => {
  const name = element['name'];
  const roadAddress = element['roadAddress'];
  const startWeekday = element['startWeekday'];
  const endWeekday = element['endWeekday'];
  const startHoliday = element['startHoliday'];
  const endHoliday = element['endHoliday'];
  const isUsed = element['isUsed'];
  const start = element['start'];
  const end = element['end'];
  const hotAirCnt = element['hotAirCnt'];
  const heaterCnt = element['heaterCnt'];
  const fireplaceCnt = element['fireplaceCnt'];
  const radiatorCnt = element['radiatorCnt'];

  const convert = (e) => {
    if (e === 'Y') {
        return "개방";
    }
    return "미개방";
  };

  const content = [
    `<div>`,
    `<h2>${name}</h2>`,
    `<p>주소 : ${roadAddress}</p>`,
    `<p>개방 일시 : ${start} ~ ${end}</p>`,
    `<p>평일 : ${startWeekday} ~ ${endWeekday}</p>`,
    `<p>주말 : ${startHoliday} ~ ${endHoliday}</p>`,
    `<p>열풍기 개수 : ${hotAirCnt}</p>`,
    `<p>히터 개수 : ${heaterCnt}</p>`,
    `<p>난로 개수 : ${fireplaceCnt}</p>`,
    `<p>라디에이터 개수 : ${radiatorCnt}</p>`,
    `<p>개방 여부 : ${convert(isUsed)}</p>`,
    `</div>`
  ].join('');

  return content;
};

const ColdMap = () => {
  const mapRef = useRef(null);
  const [myLat, setMyLat] = useState(-1);
  const [myLong, setMyLong] = useState(-1);

  useEffect(() => {
    const success = (position) => {
      const lat = position.coords.latitude;
      const long = position.coords.longitude;

      setMyLat(lat);
      setMyLong(long);
    }

    const fail = () => {
      alert('현재 위치 파악에 실패했습니다.');
    };

    navigator.geolocation.getCurrentPosition(success, fail);
  }, []);

  useEffect(() => {
    const {naver} = window;

    if (mapRef.current && naver) {
      if (myLat === -1 || myLong === -1) {
        return;
      }

      let testLat = 37.561712;
      let testLong = 127.027355;

      const location = new naver.maps.LatLng(testLat, testLong);

      const map = new naver.maps.Map(mapRef.current, {
        center: location,
          zoom: 17,
      });

      const myLocationMarker = new naver.maps.Marker({
        position: location,
        map: map,
        icon: {
          content: `<img src="${icon}" style="width: 50px; height: 50px;"/>`,
          size: new naver.maps.Size(10, 10)
        }
      });

      const URL = `http://localhost:8080/api/v1/cold/${testLat}/${testLong}`;

      fetch(URL)
      .then(res => res.json())
      .then((data) => {
        for (let element of data) {
          const la = element['latitude'];
          const lo = element['longitude'];

          let infoWindow = new naver.maps.InfoWindow({
            content: makeTemplate(element)
          });

          let marker = new naver.maps.Marker({
            position: new naver.maps.LatLng(la, lo),
            map: map
          });

          naver.maps.Event.addListener(marker, "click", (e) => {
            if (infoWindow.getMap()) {
              infoWindow.close();
            } else {
              infoWindow.open(map, marker);
            }
          });
        }
      });
    }
  }, [myLat, myLong]);

  return (
    <>
      <div className='map_container'>
        <h3>지도</h3>
        <div className='map' ref={mapRef} style={{width: 800 + 'px', height: 600 + 'px'}}></div>
      </div>
    </>
  )
};

export default ColdMap;
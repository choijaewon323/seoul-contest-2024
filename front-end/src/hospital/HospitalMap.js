import { useEffect, useRef, useState } from 'react';
import icon from '../icon.png';

const makeTemplate = (element) => {
  const name = element['name'];
  const roadAddress = element['roadAddress'];
  const monday = element['monday'];
  const tuesday = element['tuesday'];
  const wednesday = element['wednesday'];
  const thursday = element['thursday'];
  const friday = element['friday'];
  const saturday = element['saturday'];
  const sunday = element['sunday'];
  const holiday = element['holiday'];
  const tel = element['tel'];
  const emergencyTel = element['emergencyTel'];
  const info = element['info'];

  const content = [
    `<div>`,
    `<h2>${name}</h2>`,
    `<p>주소 : ${roadAddress}</p>`,
    `<p>대표 전화 : ${tel}, 응급실 전화 : ${emergencyTel}</p>`,
    `<p>월요일 : ${monday}</p>`,
    `<p>화요일 : ${tuesday}</p>`,
    `<p>수요일 : ${wednesday}</p>`,
    `<p>목요일 : ${thursday}</p>`,
    `<p>금요일 : ${friday}</p>`,
    `<p>토요일 : ${saturday}</p>`,
    `<p>일요일 : ${sunday}</p>`,
    `<p>휴일 : ${holiday}</p>`,
    `<p>비고 : ${info}</p>`,
    `</div>`
  ].join('');

  return content;
};

const HospitalMap = () => {
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

      const URL = `http://localhost:8080/api/v1/hospital/near/${testLat}/${testLong}`;

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
        <div className='map' ref={mapRef}></div>
      </div>
    </>
  )
};

export default HospitalMap;
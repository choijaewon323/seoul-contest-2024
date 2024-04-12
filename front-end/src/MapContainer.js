import { useEffect, useRef, useState } from 'react';
import icon from './icon.png';
import './MapContainer.css';

const makeTemplate = (id, name, roadAddress, oldAddress, la, lo) => {
  const content = [
    '<div>',
    `  <h1>${name}</h1>`,
    `  <p>도로명 주소 : ${roadAddress}</p>`,
    `  <p>지번 주소 : ${oldAddress}</p>`,
    `</div>`
  ].join('');

  return content;
};

const MapContainer = () => {
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
    const { naver } = window;

    if (mapRef.current && naver) {
      if (myLat !== -1 && myLong !== -1) {

        let myLat = 37.561712;
        let myLong = 127.027355;

        const location = new naver.maps.LatLng(myLat, myLong);
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

        const URL = "http://localhost:8080/api/v1/hot/" + myLat + "/" + myLong;

        fetch(URL)
          .then(res => res.json())
          .then((data) => {
            for (let element of data) {
              const id = element['id'];
              const name = element['name'];
              const roadAddress = element['roadAddress'];
              const oldAddresss = element['oldAddress'];
              const la = element['latitude'];
              const lo = element['longitude'];

              let infoWindow = new naver.maps.InfoWindow({
                content: makeTemplate(id, name, roadAddress, oldAddresss, la, lo)
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
    }
  }, [myLat, myLong]);

  return (
    <>
      <div className='map_container'>
        <h3>지도</h3>
        <div className='map' ref={mapRef} style={{width: 800 + 'px', height: 600 + 'px'}}></div>
      </div>
    </>
  );
};

export default MapContainer;
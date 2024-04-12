import { useEffect, useState } from "react";

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

  const result = <>
    <li>
      <div>
        <h2>{name}</h2>
        <p>주소 : {roadAddress}</p>
        <p>대표 전화 : {tel}, 응급실 전화 : {emergencyTel}</p>
        <p>월요일 : {monday}</p>
        <p>화요일 : {tuesday}</p>
        <p>수요일 : {wednesday}</p>
        <p>목요일 : {thursday}</p>
        <p>금요일 : {friday}</p>
        <p>토요일 : {saturday}</p>
        <p>일요일 : {sunday}</p>
        <p>휴일 : {holiday}</p>
        <p>비고 : {info}</p>
      </div>
    </li>

  </>

  return result;
};

const HospitalClosest = () => {
  const [myLat, setMyLat] = useState(-1);
  const [myLong, setMyLong] = useState(-1);
  const [list, setList] = useState([]);

  useEffect(() => {
    const success = (position) => {
      const lat = position.coords.latitude;
      const long = position.coords.longitude;

      setMyLat(lat);
      setMyLong(long);
    };

    const fail = () => {
      alert('현재 위치 파악에 실패했습니다.');
    };

    navigator.geolocation.getCurrentPosition(success, fail);
  }, []);

  useEffect(() => {
    if (myLat === -1 || myLong === -1) {
      return;
    }

    let testLat = 37.561712;
    let testLong = 127.027355;

    const URL = `http://localhost:8080/api/v1/hospital/near/${testLat}/${testLong}`;

    fetch(URL)
      .then(res => res.json())
      .then((data) => {
        if (data.length < 3) {
          return;
        }

        let result = [];
        for (let element of data) {
          const temp = makeTemplate(element);
          result.push(temp);
        }

        setList(result);
      });

  }, [myLat, myLong]);

  return (
    <>
      <div className="closest">
        <h2>근처 병원</h2>
        <ul className="closest_list">
          {list}
        </ul>
      </div>
    </>
  )
};

export default HospitalClosest;
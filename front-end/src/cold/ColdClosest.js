import { useEffect, useState } from "react";

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

  const result = <>
    <li>
      <div>
        <h2>{name}</h2>
        <p>주소 : {roadAddress}</p>
        <p>개방 일시 : {start} ~ {end}</p>
        <p>평일 : {startWeekday} ~ {endWeekday}</p>
        <p>주말 : {startHoliday} ~ {endHoliday}</p>
        <p>열풍기 개수 : {hotAirCnt}</p>
        <p>히터 개수 : {heaterCnt}</p>
        <p>난로 개수 : {fireplaceCnt}</p>
        <p>라디에이터 개수 : {radiatorCnt}</p>
        <p>개방 여부 : {convert(isUsed)}</p>
      </div>
    </li>
  </>

  return result;
};

const ColdClosest = () => {
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

    const URL = `http://localhost:8080/api/v1/cold/near/${testLat}/${testLong}`;

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
        <h2>근처 쉼터</h2>
        <ul className="closest_list">
          {list}
        </ul>
      </div>
    </>
  )
};

export default ColdClosest;
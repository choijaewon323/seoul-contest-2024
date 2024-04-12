import { useEffect, useState } from "react";

const Weather = () => {
  const [myLat, setMyLat] = useState(-1);
  const [myLong, setMyLong] = useState(-1);
  const [temperature, setTemperature] = useState(0);

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
    if (myLat !== -1 && myLong !== -1) {
      const URL = "http://localhost:8080/api/v1/weather/" + myLat + "/" + myLong;

      fetch(URL)
        .then(res => res.json())
        .then(value => {
          setTemperature(value);
        });
    }
  }, [myLat, myLong]);

  return (
    <>
      <div className="weather">
        <p>현재 위치 온도</p>
        <h2>{temperature}°C</h2>
      </div>
    </>
  )
};

export default Weather;
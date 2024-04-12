import MapContainer from './MapContainer';
import Logo from './Logo';
import Weather from './Weather';
import Nav from './Nav';
import Closest from './Closest';
import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import HotTitle from './hot/HotTitle';
import ColdTitle from './cold/ColdTitle';
import HospitalTitle from './hospital/HospitalTitle';
import HotMap from './hot/HotMap';
import ColdMap from './cold/ColdMap';
import HospitalMap from './hospital/HospitalMap';
import HotClosest from './hot/HotClosest';
import ColdClosest from './cold/ColdClosest';
import HospitalClosest from './hospital/HospitalClosest';

const App = () => {
  return (
    <>
      <div className="container">
        <BrowserRouter>
        <div className='top'>
          <Logo></Logo>
          <Nav></Nav>      
        </div>

        <div className='title'>
          <Routes>
            <Route path="/hot" element={<HotTitle></HotTitle>}></Route>
            <Route path="/cold" element={<ColdTitle></ColdTitle>}></Route>
            <Route path="/hospital" element={<HospitalTitle></HospitalTitle>}></Route>
            <Route path='*' element={<HotTitle></HotTitle>}></Route>
          </Routes>
        </div>

        <Routes>
          <Route path='/hot' element={<HotMap></HotMap>}></Route>
          <Route path='/cold' element={<ColdMap></ColdMap>}></Route>
          <Route path='/hospital' element={<HospitalMap></HospitalMap>}></Route>
          <Route path='*' element={<HotMap></HotMap>}></Route>
        </Routes>

        <Weather></Weather>

        <Routes>
          <Route path='/hot' element={<HotClosest></HotClosest>}></Route>
          <Route path='/cold' element={<ColdClosest></ColdClosest>}></Route>
          <Route path='/hospital' element={<HospitalClosest></HospitalClosest>}></Route>
          <Route path='*' element={<HotClosest></HotClosest>}></Route>
        </Routes>
        </BrowserRouter>
      </div>
    </>
  )
};

export default App;
import { Link } from 'react-router-dom';

const Nav = () => {
    return (
        <>
            <ul className="nav">
                <li><Link to={"/hot"}>무더위쉼터</Link></li>
                <li><Link to={"/cold"}>한파쉼터</Link></li>
                <li><Link to={"/hospital"}>병원</Link></li>
            </ul>
        </>
    )
};

export default Nav;
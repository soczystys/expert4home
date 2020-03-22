import React from "react";
import {UserType} from "./Root";
import Search from "../components/Search";
import ProfileList from "../components/ProfileList";

export default function HomeView() {
    let userType = UserType.CLIENT;
    const profiles=[
        {image:"https://learnreduxwithdanabramov.com/static/dan-abramov-photo.png",
            name:"Dan Abramov",
            description:"Making React accessible for users and developers at https://reach.tech . Online learning, workshops, OSS, and consulting.",
            rank:"4"},
        {image:"https://learnreduxwithdanabramov.com/static/dan-abramov-photo.png",
            name:"Dan Abramov",
            description:"Making React accessible for users and developers at https://reach.tech . Online learning, workshops, OSS, and consulting.",
            rank:"4"
        },
        {image:"https://learnreduxwithdanabramov.com/static/dan-abramov-photo.png",
            name:"Dan Abramov",
            description:"Making React accessible for users and developers at https://reach.tech . Online learning, workshops, OSS, and consulting.",
            rank:"4"
        }


    ];

    return (   <>
        <Search></Search>
        <ProfileList items={profiles}></ProfileList>

    </>);
}
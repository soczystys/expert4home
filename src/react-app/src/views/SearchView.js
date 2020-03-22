import React from "react";
import ProfileCard from "../components/ProfileCard";
import Search from "../components/Search"
import ProfileList from "../components/ProfileList";

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
    ,
    {image:"https://learnreduxwithdanabramov.com/static/dan-abramov-photo.png",
        name:"Dan Abramov",
        description:"Making React accessible for users and developers at https://reach.tech . Online learning, workshops, OSS, and consulting.",
        rank:"4"
    }
    ,
    {image:"https://learnreduxwithdanabramov.com/static/dan-abramov-photo.png",
        name:"Dan Abramov",
        description:"Making React accessible for users and developers at https://reach.tech . Online learning, workshops, OSS, and consulting.",
        rank:"4"
    }

    ];




export default function SearchView() {

    return (
        <>
            <Search></Search>
            <ProfileList items={profiles}></ProfileList>
        </>
    )


}
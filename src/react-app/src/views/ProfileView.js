import React from "react";
import ProfileOverview from "../components/ProfileOverview";
import CommentsList from "../components/CommentsList";

export default function ProfileView() {

    const profiles=[
        {image:"https://learnreduxwithdanabramov.com/static/dan-abramov-photo.png",
            rank:"5",
            name:"Dan Abramov",
            date:"5.12.19",
            description:"Almost perfect! Totally recommend for children, IGN 10/10"
        },
        {image:"https://learnreduxwithdanabramov.com/static/dan-abramov-photo.png",
            rank:"2",
            name:"Vomarba Nad",
            date:"5.12.19",
            description:"Still better love story than Twilight"
        },
        {image:"https://learnreduxwithdanabramov.com/static/dan-abramov-photo.png",
            rank:"3",
            name:"Toward Hodd",
            date:"5.12.19",
            description:"It just works, as usual!"
        },

    ];

    return (<>
    <ProfileOverview image="https://learnreduxwithdanabramov.com/static/dan-abramov-photo.png" 
    rank="4" name="Dan Abramov" spec="Front-End Developer" contact="111-222-333"
    description="Making React accessible for users and developers at https://reach.tech . Online learning, workshops, OSS, and consulting."/>
    <CommentsList items={profiles}></CommentsList>
    </>
    );
}

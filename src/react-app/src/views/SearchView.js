import React from "react";
import ProfileCard from "../components/Navigation/ProfileCard";

export default function SearchView() {
    return  (
            <>
                <span>Profile</span>
                <ProfileCard image="https://learnreduxwithdanabramov.com/static/dan-abramov-photo.png"
                             name="Dan Abramov"
                             description="Making React accessible for users and developers at https://reach.tech . Online learning, workshops, OSS, and consulting."
                             rank = "4"
                > </ProfileCard>
            </>
        )

}

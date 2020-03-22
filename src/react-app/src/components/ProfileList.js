import React from "react";
import ProfileCard from "../components/ProfileCard";

import Grid from '@material-ui/core/Grid';

const ProfileList=({items})=> (
        <>
            <Grid
                container
                direction="row"
                justify="space-around"
                alignItems="flex-start"
            >
                {items.map(item => {
                    return (
                        <Grid item>
                            <ProfileCard {...item}/>
                        </Grid>
                        )
                    }
                )}
            </Grid>


        </>
    )


export default ProfileList;
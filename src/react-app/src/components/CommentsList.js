import React from "react";
import ProfileComment from "../components/ProfileComment";

import Grid from '@material-ui/core/Grid';

const CommentsList=({items})=> (
        <>
            <Grid
                container
                direction="column"
                justify="space-evenly"
                alignItems="flex-start"
            >
                {items.map(item => {
                    return (
                        <Grid item>
                            <ProfileComment {...item}/>
                        </Grid>
                        )
                    }
                )}
            </Grid>


        </>
    )

    export default CommentsList;
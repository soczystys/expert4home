import React from 'react';
import Avatar from '@material-ui/core/Avatar';
import Rating from '@material-ui/lab/Rating';
import Card from '@material-ui/core/Card';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Box from "@material-ui/core/Box";
import { red } from '@material-ui/core/colors';
import Divider from "@material-ui/core/Divider";

const useStyles = makeStyles(()=>({
    root: {
        minWidth:600,
        maxWidth:600,
        borderRadius: 20,
        display:"flex",
        flexDirection: "row",
        padding:5,
        boxShadow: "0 3px 6px rgba(0,0,0,0.16), 0 3px 6px rgba(0,0,0,0.23)",
        marginLeft: 40,
        marginBottom: 10,
    },
    column: {
        display:"flex",
        flexDirection: "column",
        margin: 5,
    },
    img:{
        height:100,
        width:100,
        alignSelf:"center"
    },
    name:{
        alignSelf: "center",
    },
    description:{
        color:"black",
    },
    rating:{
      
    },
    bonusInfo:{
        color: "gray",
    }

}))

const ProfileComment= ({image,rank, name, date, description}) => {
    const CommentStyles = useStyles();
    return (
        <>
            <Card boxShadow={3} variant="outlined" className={CommentStyles.root}>
                <Box className={CommentStyles.column}>
                    <Avatar className={CommentStyles.img} src={image} />
                    <Typography variant="body1" className={CommentStyles.name}>
                        {name}
                    </Typography>
                </Box>
                <Box className={CommentStyles.column}> 
                    <Typography variant="body1" className={CommentStyles.bonusInfo}>
                        {date}
                    </Typography>
                    <Typography variant="body1" className={CommentStyles.description}>
                        {description}
                    </Typography>
                    <Divider/>
                    <Rating className={CommentStyles.rating} name="ocena" value={rank} disabled/>
                </Box>
            </Card>

        </>
    )
};

export default ProfileComment;
import React from 'react';
import Avatar from '@material-ui/core/Avatar';
import Card from '@material-ui/core/Card';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Divider from "@material-ui/core/Divider";
import Rating from '@material-ui/lab/Rating';
import Button from '@material-ui/core/Button';




const useStyles = makeStyles(()=>({
    root: {
        minWidth:250,
        maxWidth:380,
        minHeight:400,
        borderRadius: 20,
        display:"flex",
        flexDirection: "column",
        padding:10,
        justifyContent:"space-around",
        boxShadow: "0 3px 6px rgba(0,0,0,0.16), 0 3px 6px rgba(0,0,0,0.23)",
        margin:20,
    },
    content:{

    },
    img:{
        height:100,
        width:100,
        alignSelf:"center"
    },
    description:{
        color:"gray"
    },
    rating:{
      alignSelf:"center",
    }

}))


const ProfileCard = ({image,name,description,rank}) => {
    const cardStyles = useStyles();
    return (
        <>
                <Card boxShadow={3} variant="outlined" className={cardStyles.root}>
                    <Avatar className={cardStyles.img} alt={name} src={image} />
                    <Typography variant="h4">
                        {name}
                    </Typography>
                    <Divider/>
                    <Typography variant="body1" className={cardStyles.description}>
                        {description}
                    </Typography>
                    <Rating className={cardStyles.rating} name="ocena" value={rank} disabled/>
                    <Button variant="contained" color="primary">
                        Zobacz profil!
                    </Button>
                </Card>


        </>
    )
};

export default ProfileCard;
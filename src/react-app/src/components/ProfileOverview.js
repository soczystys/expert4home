import React from 'react';
import Avatar from '@material-ui/core/Avatar';
import Rating from '@material-ui/lab/Rating';
import Card from '@material-ui/core/Card';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Box from "@material-ui/core/Box";
import { red } from '@material-ui/core/colors';
import Divider from "@material-ui/core/Divider";
import { Link } from 'react-router-dom'

const useStyles = makeStyles(()=>({
    root: {
        minWidth:500,
        maxWidth:1000,
        borderRadius: 20,
        display:"flex",
        flexDirection: "row",
        padding:15,
        justifyContent:"space-around",
        boxShadow: "0 3px 6px rgba(0,0,0,0.16), 0 3px 6px rgba(0,0,0,0.23)",
        margin:20,
        marginBottom: 60,
    },
    column: {
        display:"flex",
        flexDirection: "column",
        margin: 15,
    },
    img:{
        height:200,
        width:200,
        alignSelf:"center",
    },
    description:{
        color:"gray",
    },
    rating:{
      alignSelf:"center",
    },
    bonusInfo:{
        color: "brown",
    },
    link:{
        color:"white",
        textDecoration:"none",
        width: "100%",
    },
    buttonBox:{
        width: "50%",
        marginLeft: "50%"
    }

}))

const ProfileOverview = ({image,rank, name, spec, contact, description}) => {
    const overviewStyles = useStyles();
    return (
        <>
            <Card boxShadow={3} variant="outlined" className={overviewStyles.root}>
                <Box className={overviewStyles.column}>
                    <Avatar className={overviewStyles.img} src={image} />
                    <Rating className={overviewStyles.rating} name="ocena" value={rank} disabled/>
                </Box>
                <Box className={overviewStyles.column}> 
                <Button className={overviewStyles.buttonBox} variant="contained" color="primary">
                    <Link className={overviewStyles.link} to={{pathname: `/app/profile`}} >Wyślij zgłoszenie!</Link>
                </Button>
                <Typography variant="h3">
                    {name}
                </Typography>
                <Divider/>
                <Typography variant="body1" className={overviewStyles.bonusInfo}>
                    {spec}
                </Typography>
                <Typography variant="body1" className={overviewStyles.bonusInfo}>
                    {contact}
                </Typography>
                <Divider/>
                <Typography variant="body1" className={overviewStyles.description}>
                    {description}
                </Typography>
                </Box>
            </Card>

        </>
    )
};

export default ProfileOverview;
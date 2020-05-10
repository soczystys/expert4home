import React from 'react';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
import Link from '@material-ui/core/Link';
import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';

const useStyles = makeStyles((theme) => ({
    root: {
        height: '100vh',
    },
    image: {
        backgroundImage: 'url(home-page-background.jpg)',
        backgroundRepeat: 'no-repeat',
        backgroundColor:
            theme.palette.type === 'light' ? theme.palette.grey[50] : theme.palette.grey[900],
        backgroundSize: 'cover',
        backgroundPosition: 'center',
    },
    paper: {
        margin: theme.spacing(8, 4),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
    },
    avatar: {
        margin: theme.spacing(1),
        backgroundColor: theme.palette.secondary.main,
    },
    form: {
        width: '100%', // Fix IE 11 issue.
        marginTop: theme.spacing(1),
    },
    submit: {
        margin: theme.spacing(3, 0, 2),
    },
}));

function getCookie(name) {
    if (!document.cookie) {
        return null;
    }

    const cookies = document.cookie.split(';')
        .map(c => c.trim())
        .filter(c => c.startsWith(name + '='));

    if (cookies.length === 0) {
        return null;
    }

    return decodeURIComponent(cookies[0].split('=')[1]);
}

function getParams(url) {
    let params = {};
    let parser = document.createElement('a');
    parser.href = url;
    const query = parser.search.substring(1);
    const vars = query.split('&');
    for (let i = 0; i < vars.length; i++) {
        const pair = vars[i].split('=');
        params[pair[0]] = decodeURIComponent(pair[1]);
    }
    return params;
}

function getForm(classes) {
    const xsrfToken = getCookie('XSRF-TOKEN');
    const params = getParams(window.location.href);
    const isRegister = params.register === "true";
    const isLogout = params.logout === "true";
    const isError = params.error === "true";

    if (isRegister) {
        return (
            <form className={classes.form} method="post" action="/process_register">
                <input name="_csrf" type="hidden" value={xsrfToken} />
                <Button
                    type="submit"
                    fullWidth
                    variant="contained"
                    color="primary"
                    className={classes.submit}
                >
                    Zarejestruj się
                </Button>
                {isError &&
                    <Grid container>
                        <Grid item xs />
                        <Grid item>
                            <Link href="#" variant="body2" color="error">
                                {"Rejestracja nie powiodła się. Wystąpił błąd."}
                            </Link>
                        </Grid>
                        <Grid item xs />
                    </Grid>
                }
                <Grid container>
                    <Grid item xs />
                    <Grid item>
                        <Link href="/home?login=true" variant="body2">
                            {"Posiadasz już konto? Zaloguj się!"}
                        </Link>
                    </Grid>
                    <Grid item xs />
                </Grid>
            </form>
        );
    } else {
        return (
            <form className={classes.form} method="post" action="/process_login">
                <input name="_csrf" type="hidden" value={xsrfToken} />
                <TextField
                    variant="outlined"
                    margin="normal"
                    required
                    fullWidth
                    id="username"
                    label="Nazwa użytkownika"
                    name="username"
                    autoFocus
                />
                <TextField
                    variant="outlined"
                    margin="normal"
                    required
                    fullWidth
                    name="password"
                    label="Hasło"
                    type="password"
                    id="password"
                />
                <FormControlLabel
                    control={<Checkbox name="remember-me" color="primary" />}
                    label="Zapamiętaj mnie"
                />
                <Button
                    type="submit"
                    fullWidth
                    variant="contained"
                    color="primary"
                    className={classes.submit}
                >
                    Zaloguj się
                </Button>
                {isError &&
                    <Grid container>
                        <Grid item xs />
                        <Grid item>
                            <Link href="#" variant="body2" color="error">
                                {"Wprowadzono niepoprawną nazwę użytkownika lub hasło."}
                            </Link>
                        </Grid>
                        <Grid item xs />
                    </Grid>
                }
                <Grid container>
                    <Grid item xs />
                    <Grid item>
                        <Link href="/home?register=true" variant="body2">
                            {"Nie posiadasz konta? Zarejestruj się!"}
                        </Link>
                    </Grid>
                    <Grid item xs />
                </Grid>
            </form>
        );
    }
}

export default function HomeView() {
    const classes = useStyles();

    return (
        <Grid container component="main" className={classes.root}>
            <CssBaseline />
            <Grid item xs={false} sm={4} md={7} className={classes.image} />
            <Grid item xs={12} sm={8} md={5} component={Paper} elevation={6} square>
                <div className={classes.paper}>
                    <Avatar className={classes.avatar}>
                        <LockOutlinedIcon />
                    </Avatar>
                    <Typography component="h1" variant="h5">
                        Expert4Home
                    </Typography>
                    {getForm(classes)}
                </div>
            </Grid>
        </Grid>
    );
}

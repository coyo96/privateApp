import { Button, StyleSheet } from 'react-native';

import EditScreenInfo from '../../components/EditScreenInfo';
import { Text, View } from '../../components/Themed';
import { useAuth0 } from 'react-native-auth0'
import { useState } from 'react';

export default function TabOneScreen() {
  const { authorize, clearSession, getCredentials, user, error, isLoading} = useAuth0();
  const [accessToken, setAccessToken] = useState<string | undefined>("");
  const onLogin = async () => {
    try {
      await authorize({scope: "openid profile offline_access", audience: 'http://localhost:8080'});
      const credentials = await getCredentials();
      setAccessToken(credentials?.accessToken)
      console.log(credentials?.scope)
    } catch (e) {
      console.log(e);
    }
  };
  const registerNewUser = async () => {
    const newUser = {
      username: user?.name,
      email: user?.email,
      email_verified: user?.emailVerified,
      first_name: user?.givenName,
      last_name: user?.familyName,
      date_of_birth: new Date("06/26/1997"),
      primary_phone: 14807101780,
      gender_code: "m",
      activated: true,
      picture: user?.picture,
      user_address: user?.address
    }
    const response = await fetch("http://192.168.1.117:8080/api/user/register", {
      method: "POST", 
      headers: {
        "Content-type": "application/json", 
        authorization: "Bearer " + accessToken},
        body: JSON.stringify(newUser)
      })
    console.log(JSON.stringify(response));
  }

  const onLogout = async () => {
    try {
      await clearSession();
    } catch (e) {
      console.log('Log out cancelled');
    }
  };

  if (isLoading) {
    return <View style={styles.container}><Text>Loading</Text></View>;
  }

  const loggedIn = user !== undefined && user !== null;
  return (
    <View style={styles.container}>
      <Text style={styles.title}>Tab One</Text>
      <View style={styles.separator} lightColor="#eee" darkColor="rgba(255,255,255,0.1)" />
      <EditScreenInfo path="app/(tabs)/index.tsx" />

      {loggedIn && <Text>You are logged in as {user.name}</Text>}
      {!loggedIn && <Text>You are not logged in</Text>}
      {error && <Text>{error.message}</Text>}

      <Button
        onPress={loggedIn ? onLogout : onLogin}
        title={loggedIn ? 'Log Out' : 'Log In'}
      />
      <Button 
        onPress={registerNewUser}
        title="Call Api"
        />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  title: {
    fontSize: 20,
    fontWeight: 'bold',
  },
  separator: {
    marginVertical: 30,
    height: 1,
    width: '80%',
  },
});

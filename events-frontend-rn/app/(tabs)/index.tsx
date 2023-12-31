import { Button, StyleSheet } from 'react-native';

import EditScreenInfo from '../../components/EditScreenInfo';
import { Text, View } from '../../components/Themed';
import { useAuth0 } from 'react-native-auth0'
import { useToken } from '../../hooks/useToken';
import { isRegistered } from '../../service/RegistrationService';
import { router } from 'expo-router';

export default function TabOneScreen() {
  const { authorize, clearSession, getCredentials, user, error, isLoading} = useAuth0();
  const { accessToken, setAccessToken } = useToken();
  const loggedIn = user !== undefined && user !== null;
  const onLogin = async () => {
    try {
      await authorize({scope: "openid profile offline_access", audience: 'http://localhost:8080'});
      const credentials = await getCredentials();
      setAccessToken(credentials?.accessToken);
      if(accessToken) {
        if(await isRegistered(accessToken) !== true) {
          router.push("/registration/")
        }
      }
    } catch (e) {
      console.log(e);
    }
  };

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
        onPress={() => router.push("/registration/")}
        title="go to registration"
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

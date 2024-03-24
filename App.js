import { StatusBar } from "expo-status-bar";
import { Button, StyleSheet, Text, View } from "react-native";
import {NativeModules} from 'react-native';
const {WidgetModule} = NativeModules;
import Response from './android/app/src/main/java/com/anonymous/myapp/response.json';

export default function App() {
  const onPress = () => {
    WidgetModule.storeDriverWidgetUUIDAndApiKey("9e41928d-f87f-48c9-9b9f-ba4969cd6ae2","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InJjcWR3bHl6eHRleGhkbGNoeGhqIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MDI2NDI3MzMsImV4cCI6MjAxODIxODczM30.3iDaj744eR1JwzIGkEj50WkOpkMyFLKUjY6NsmGP8kY")
    WidgetModule.storeTeamWidgetUUIDAndApiKey("9e41928d-f87f-48c9-9b9f-ba4969cd6ae2","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InJjcWR3bHl6eHRleGhkbGNoeGhqIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MDI2NDI3MzMsImV4cCI6MjAxODIxODczM30.3iDaj744eR1JwzIGkEj50WkOpkMyFLKUjY6NsmGP8kY")
    WidgetModule.storeApiData(JSON.stringify(Response));
  };

  return (
    <View style={styles.container}>
      <Text>Hi there! welcome to the app</Text>
      <StatusBar style="auto" />

      <Button
        title="Click to invoke your native module!"
        color="#841584"
        onPress={onPress}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#fff",
    alignItems: "center",
    justifyContent: "center",
  },
});

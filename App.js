import { StatusBar } from "expo-status-bar";
import { Button, StyleSheet, Text, View } from "react-native";
import {NativeModules} from 'react-native';
const {WidgetModule} = NativeModules;
import Response from './android/app/src/main/java/com/anonymous/myapp/response.json';

export default function App() {
  const onPress = () => {
    console.log("We will invoke the native module here!");
    WidgetModule.updateSmallCalenderWidget('testName', 'testLocation', JSON.stringify(Response));
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

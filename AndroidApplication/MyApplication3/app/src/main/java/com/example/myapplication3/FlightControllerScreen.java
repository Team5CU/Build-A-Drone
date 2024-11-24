package com.example.myapplication3;

import android.Manifest;
import android.widget.Toast;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import java.util.UUID;

public class FlightControllerScreen extends AppCompatActivity {
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothGatt bluetoothGatt;
    private BluetoothGattCharacteristic writeCharacteristic;
    private static final UUID SERVICE_UUID = UUID.fromString("c189ed91-d311-4d4c-9a7e-2b8ce6714ed3");
    private static final UUID CHARACTERISTIC_UUID = UUID.fromString("6e400002-b5a3-f393-e0a9-e50e24dcca9e");

    private JoystickView joystick1;
    private JoystickView joystick2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_controller_screen);

        // Bluetooth Connection
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice("D8:3A:DD:69:F6:16"); // Replace with your device address
        if (checkPermission()) {
            bluetoothGatt = device.connectGatt(this, false, gattCallback);
        }

        setupUI();
    }

    private void setupUI() {
        joystick1 = findViewById(R.id.joystickView);  // Left joystick
        joystick2 = findViewById(R.id.joystickView2); // Right joystick

        findViewById(R.id.button_exit).setOnClickListener(v ->
                startActivity(new Intent(FlightControllerScreen.this, MainActivity.class))
        );

        findViewById(R.id.button_Land).setOnClickListener(v -> sendCommand("Land"));
        findViewById(R.id.button_Takeoff).setOnClickListener(v -> sendCommand("Takeoff"));

        joystick1.setOnMoveListener((x, y) -> sendJoystickPosition("Joystick1", x, y));
        joystick2.setOnMoveListener((x, y) -> sendJoystickPosition("Joystick2", x, y));
    }

    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED;
    }

    private final BluetoothGattCallback gattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            runOnUiThread(() -> {
                if (newState == BluetoothGatt.STATE_CONNECTED) {
                    Toast.makeText(FlightControllerScreen.this, "Connected to device", Toast.LENGTH_SHORT).show();
                    if (checkPermission()) {
                        gatt.discoverServices();
                    }
                } else if (newState == BluetoothGatt.STATE_DISCONNECTED) {
                    Toast.makeText(FlightControllerScreen.this, "Disconnected from device", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            BluetoothGattService service = gatt.getService(SERVICE_UUID);
            if (service != null) {
                writeCharacteristic = service.getCharacteristic(CHARACTERISTIC_UUID);
            }
        }
    };

    private void sendJoystickPosition(String joystickName, int x, int y) {
        if (writeCharacteristic == null) {
            Toast.makeText(this, "Bluetooth not ready", Toast.LENGTH_SHORT).show();
            return;
        }

        String command = joystickName + ":X=" + x + ",Y=" + y;
        writeCharacteristic.setValue(command.getBytes());
        if (checkPermission()) {
            bluetoothGatt.writeCharacteristic(writeCharacteristic);
        }
    }

    private void sendCommand(String command) {
        if (writeCharacteristic == null) {
            Toast.makeText(this, "Bluetooth not ready", Toast.LENGTH_SHORT).show();
            return;
        }

        writeCharacteristic.setValue(command.getBytes());
        if (checkPermission()) {
            bluetoothGatt.writeCharacteristic(writeCharacteristic);
        }
    }

}
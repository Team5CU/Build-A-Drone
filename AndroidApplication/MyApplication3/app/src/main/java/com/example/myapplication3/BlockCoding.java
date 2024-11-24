package com.example.myapplication3;

import android.Manifest;
import android.widget.Button;
import android.widget.Toast;
import android.view.View;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.UUID;

public class BlockCoding extends AppCompatActivity {
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothGatt bluetoothGatt;
    private BluetoothGattCharacteristic writeCharacteristic;
    private static final UUID SERVICE_UUID = UUID.fromString("c189ed91-d311-4d4c-9a7e-2b8ce6714ed3");
    private static final UUID CHARACTERISTIC_UUID = UUID.fromString("6e400002-b5a3-f393-e0a9-e50e24dcca9e");
    private ArrayList<String> selectedItems = new ArrayList<>();
    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_coding);

        // Bluetooth Connection
        requestPermissionsIfNeeded();
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();

        if (bluetoothAdapter != null) {
            BluetoothDevice device = bluetoothAdapter.getRemoteDevice("D8:3A:DD:69:F6:16"); ////D8:3A:DD:CC:11:6B (pi5) D8:3A:DD:69:F7:33 pi4
            if (checkPermission()) {
                bluetoothGatt = device.connectGatt(this, false, gattCallback);
            }
        }

        setupUI();
    }

    private void setupUI() {
        findViewById(R.id.button_exit2).setOnClickListener(v ->
                startActivity(new Intent(BlockCoding.this, MainActivity.class))
        );

        ArrayList<String> options = new ArrayList<>();
        options.add("Fly Up 1 Unit");
        options.add("Fly Down 1 Unit");
        options.add("Fly Left 1 Unit");
        options.add("Fly Right 1 Unit");
        options.add("Fly Forward 1 Unit");
        options.add("Fly Backward 1 Unit");


        ArrayAdapter<String> leftAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, selectedItems);
        ArrayAdapter<String> rightAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, options);

        ListView leftListView = findViewById(R.id.leftListView);
        leftListView.setAdapter(leftAdapter);
        ListView rightListView = findViewById(R.id.rightListView);
        rightListView.setAdapter(rightAdapter);

        rightListView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedCommand = options.get(position);
            selectedItems.add(selectedCommand);
            leftAdapter.notifyDataSetChanged();
        });

        findViewById(R.id.button_delete_last).setOnClickListener(v -> {
            if (!selectedItems.isEmpty()) {
                selectedItems.remove(selectedItems.size() - 1);
                leftAdapter.notifyDataSetChanged();
            }
        });

        findViewById(R.id.button_download).setOnClickListener(v -> sendAllCommands());
    }

    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissionsIfNeeded() {
        if (!checkPermission()) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            boolean allPermissionsGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }

            if (!allPermissionsGranted) {
                Toast.makeText(this, "Bluetooth permissions are required for this app", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private final BluetoothGattCallback gattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            runOnUiThread(() -> {
                Button downloadButton = findViewById(R.id.button_download);
                if (newState == BluetoothGatt.STATE_CONNECTED) {
                    Toast.makeText(BlockCoding.this, "Connected to device", Toast.LENGTH_SHORT).show();
                    downloadButton.setVisibility(View.VISIBLE);
                    if (checkPermission()) {
                        gatt.discoverServices();
                    }
                } else if (newState == BluetoothGatt.STATE_DISCONNECTED) {
                    Toast.makeText(BlockCoding.this, "Disconnected from device", Toast.LENGTH_SHORT).show();
                    downloadButton.setVisibility(View.GONE);
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

    private void sendAllCommands() {
        if (selectedItems.isEmpty()) {
            Toast.makeText(this, "No commands to send", Toast.LENGTH_SHORT).show();
            return;
        }

        for (String command : selectedItems) {
            writeCharacteristic.setValue(command.getBytes());
            if (checkPermission()) {
                bluetoothGatt.writeCharacteristic(writeCharacteristic);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

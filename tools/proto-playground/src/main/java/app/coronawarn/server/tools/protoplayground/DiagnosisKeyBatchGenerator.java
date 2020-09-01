package app.coronawarn.server.tools.protoplayground;

import app.coronawarn.server.tools.protoplayground.gen.DiagnosisKey;
import app.coronawarn.server.tools.protoplayground.gen.DiagnosisKeyBatch;
import com.google.protobuf.ByteString;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiagnosisKeyBatchGenerator {
  int numberOfKeys = 10;

  public static void main(String[] args) throws IOException {
    DiagnosisKeyBatchGenerator diagnosisKeyBatchGenerator = new DiagnosisKeyBatchGenerator();
    diagnosisKeyBatchGenerator.writeDiagnosisKeyBatch();
  }

  public void writeDiagnosisKeyBatch() throws IOException {
    List<DiagnosisKey> keys = new ArrayList<>();

    for (int i = 0; i < numberOfKeys; i++) {
      byte[] keyData = new byte[16];
      Random random = new Random();
      random.nextBytes(keyData);

      DiagnosisKey.Builder key = DiagnosisKey.newBuilder();
      key.setKeyData(ByteString.copyFrom(keyData));
      key.setTransmissionRiskLevel(4);
      key.setRollingStartIntervalNumber(2660474 + 144 * i);  // TODO epoch +/- 14 days / 600
      key.setRollingPeriod(144);
      key.addAllVisitedCountries(List.of("DE", "FR"));
      key.setOrigin("DE");

      keys.add(key.build());
    }

    DiagnosisKeyBatch.Builder diagnosisKeyBatch = DiagnosisKeyBatch.newBuilder();
    diagnosisKeyBatch.addAllKeys(keys);

    diagnosisKeyBatch.build()
        .writeTo(new FileOutputStream("diagnosis_key_batch.bin"));
  }
}

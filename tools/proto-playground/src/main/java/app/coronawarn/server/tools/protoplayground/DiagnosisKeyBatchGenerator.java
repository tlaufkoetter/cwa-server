package app.coronawarn.server.tools.protoplayground;

import app.coronawarn.server.tools.protoplayground.gen.DiagnosisKey;
import app.coronawarn.server.tools.protoplayground.gen.DiagnosisKeyBatch;
import app.coronawarn.server.tools.protoplayground.gen.Key2;
import app.coronawarn.server.tools.protoplayground.gen.SubmissionPayload;
import app.coronawarn.server.tools.protoplayground.gen.VerificationType;
import com.google.protobuf.ByteString;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DiagnosisKeyBatchGenerator {
  int numberOfKeys = 10;

  public static void main(String[] args) throws IOException {
    DiagnosisKeyBatchGenerator diagnosisKeyBatchGenerator = new DiagnosisKeyBatchGenerator();
    diagnosisKeyBatchGenerator.writeDiagnosisKeyBatch();
  }

  public void writeDiagnosisKeyBatch() throws IOException {
    List<DiagnosisKey> keys = new ArrayList<>();

    for (int i = 0; i < numberOfKeys; i++) {
      DiagnosisKey.Builder key = DiagnosisKey.newBuilder();
      key.setKeyData(ByteString.copyFrom(new byte[16]));
      key.setTransmissionRiskLevel(4);
      key.setRollingStartIntervalNumber(2657865 + 144 * i);
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

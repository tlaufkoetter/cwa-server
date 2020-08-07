package app.coronawarn.server.tools.protoplayground;

import app.coronawarn.server.tools.protoplayground.gen.Key2;
import app.coronawarn.server.tools.protoplayground.gen.SubmissionPayload;
import app.coronawarn.server.tools.protoplayground.gen.VerificationType;
import com.google.protobuf.ByteString;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SubmissionPayloadGenerator {
  int numberOfKeys = 10;

  public static void main(String[] args) throws IOException {
    SubmissionPayloadGenerator submissionPayloadGenerator = new SubmissionPayloadGenerator();
    submissionPayloadGenerator.getSubmissionPayload();
  }

  public void getSubmissionPayload() throws IOException {
    List<Key2> keys = new ArrayList<>();

    for (int i = 0; i < numberOfKeys; i++) {
      Key2.Builder key = Key2.newBuilder();
      key.setKeyData(ByteString.copyFrom(new byte[16]));
      key.setTransmissionRiskLevel(4);
      key.setRollingStartNumber(2657865 + 144 * i);
      key.setRollingPeriod(144);

      keys.add(key.build());
    }

    SubmissionPayload.Builder submissionPayload = SubmissionPayload.newBuilder();
    submissionPayload.addAllKeys(keys);
    submissionPayload.setPadding(ByteString.copyFrom(new byte[1000]));
    submissionPayload.addAllVisitedCountries(List.of("DE", "FR"));
    submissionPayload.setOrigin("DE");
    submissionPayload.setVerificationType(VerificationType.LAB_VERIFIED);

    submissionPayload.build()
        .writeTo(new FileOutputStream("submission_payload.bin"));
  }
}

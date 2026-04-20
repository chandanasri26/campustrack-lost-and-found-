package de.flapdoodle.embed.mongo.transitions;

import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.process.archives.ExtractedFileSet;
import de.flapdoodle.embed.process.config.SupportConfig;
import de.flapdoodle.embed.process.io.ProcessOutput;
import de.flapdoodle.embed.process.types.ProcessArguments;
import de.flapdoodle.embed.process.types.ProcessConfig;
import de.flapdoodle.embed.process.types.ProcessEnv;
import de.flapdoodle.os.Platform;
import de.flapdoodle.reverse.StateID;
import de.flapdoodle.reverse.naming.HasLabel;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Immutable implementation of {@link MongodStarter}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableMongodStarter.builder()}.
 */
@SuppressWarnings({"all"})
public final class ImmutableMongodStarter extends MongodStarter {
  private final StateID<ExtractedFileSet> processExecutable;
  private final StateID<ProcessConfig> processConfig;
  private final StateID<ProcessEnv> processEnv;
  private final StateID<ProcessArguments> arguments;
  private final StateID<ProcessOutput> processOutput;
  private final StateID<SupportConfig> supportConfig;
  private final StateID<Platform> platform;
  private final StateID<Net> net;
  private final String transitionLabel;
  private final StateID<RunningMongodProcess> destination;

  private ImmutableMongodStarter(ImmutableMongodStarter.Builder builder) {
    if (builder.processExecutable != null) {
      initShim.processExecutable(builder.processExecutable);
    }
    if (builder.processConfig != null) {
      initShim.processConfig(builder.processConfig);
    }
    if (builder.processEnv != null) {
      initShim.processEnv(builder.processEnv);
    }
    if (builder.arguments != null) {
      initShim.arguments(builder.arguments);
    }
    if (builder.processOutput != null) {
      initShim.processOutput(builder.processOutput);
    }
    if (builder.supportConfig != null) {
      initShim.supportConfig(builder.supportConfig);
    }
    if (builder.platform != null) {
      initShim.platform(builder.platform);
    }
    if (builder.net != null) {
      initShim.net(builder.net);
    }
    if (builder.transitionLabel != null) {
      initShim.transitionLabel(builder.transitionLabel);
    }
    if (builder.destination != null) {
      initShim.destination(builder.destination);
    }
    this.processExecutable = initShim.processExecutable();
    this.processConfig = initShim.processConfig();
    this.processEnv = initShim.processEnv();
    this.arguments = initShim.arguments();
    this.processOutput = initShim.processOutput();
    this.supportConfig = initShim.supportConfig();
    this.platform = initShim.platform();
    this.net = initShim.net();
    this.transitionLabel = initShim.transitionLabel();
    this.destination = initShim.destination();
    this.initShim = null;
  }

  private ImmutableMongodStarter(
      StateID<ExtractedFileSet> processExecutable,
      StateID<ProcessConfig> processConfig,
      StateID<ProcessEnv> processEnv,
      StateID<ProcessArguments> arguments,
      StateID<ProcessOutput> processOutput,
      StateID<SupportConfig> supportConfig,
      StateID<Platform> platform,
      StateID<Net> net,
      String transitionLabel,
      StateID<RunningMongodProcess> destination) {
    this.processExecutable = processExecutable;
    this.processConfig = processConfig;
    this.processEnv = processEnv;
    this.arguments = arguments;
    this.processOutput = processOutput;
    this.supportConfig = supportConfig;
    this.platform = platform;
    this.net = net;
    this.transitionLabel = transitionLabel;
    this.destination = destination;
    this.initShim = null;
  }

  private static final byte STAGE_INITIALIZING = -1;
  private static final byte STAGE_UNINITIALIZED = 0;
  private static final byte STAGE_INITIALIZED = 1;
  private transient volatile InitShim initShim = new InitShim();

  private final class InitShim {
    private byte processExecutableBuildStage = STAGE_UNINITIALIZED;
    private StateID<ExtractedFileSet> processExecutable;

    StateID<ExtractedFileSet> processExecutable() {
      if (processExecutableBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (processExecutableBuildStage == STAGE_UNINITIALIZED) {
        processExecutableBuildStage = STAGE_INITIALIZING;
        this.processExecutable = Objects.requireNonNull(ImmutableMongodStarter.super.processExecutable(), "processExecutable");
        processExecutableBuildStage = STAGE_INITIALIZED;
      }
      return this.processExecutable;
    }

    void processExecutable(StateID<ExtractedFileSet> processExecutable) {
      this.processExecutable = processExecutable;
      processExecutableBuildStage = STAGE_INITIALIZED;
    }

    private byte processConfigBuildStage = STAGE_UNINITIALIZED;
    private StateID<ProcessConfig> processConfig;

    StateID<ProcessConfig> processConfig() {
      if (processConfigBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (processConfigBuildStage == STAGE_UNINITIALIZED) {
        processConfigBuildStage = STAGE_INITIALIZING;
        this.processConfig = Objects.requireNonNull(ImmutableMongodStarter.super.processConfig(), "processConfig");
        processConfigBuildStage = STAGE_INITIALIZED;
      }
      return this.processConfig;
    }

    void processConfig(StateID<ProcessConfig> processConfig) {
      this.processConfig = processConfig;
      processConfigBuildStage = STAGE_INITIALIZED;
    }

    private byte processEnvBuildStage = STAGE_UNINITIALIZED;
    private StateID<ProcessEnv> processEnv;

    StateID<ProcessEnv> processEnv() {
      if (processEnvBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (processEnvBuildStage == STAGE_UNINITIALIZED) {
        processEnvBuildStage = STAGE_INITIALIZING;
        this.processEnv = Objects.requireNonNull(ImmutableMongodStarter.super.processEnv(), "processEnv");
        processEnvBuildStage = STAGE_INITIALIZED;
      }
      return this.processEnv;
    }

    void processEnv(StateID<ProcessEnv> processEnv) {
      this.processEnv = processEnv;
      processEnvBuildStage = STAGE_INITIALIZED;
    }

    private byte argumentsBuildStage = STAGE_UNINITIALIZED;
    private StateID<ProcessArguments> arguments;

    StateID<ProcessArguments> arguments() {
      if (argumentsBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (argumentsBuildStage == STAGE_UNINITIALIZED) {
        argumentsBuildStage = STAGE_INITIALIZING;
        this.arguments = Objects.requireNonNull(ImmutableMongodStarter.super.arguments(), "arguments");
        argumentsBuildStage = STAGE_INITIALIZED;
      }
      return this.arguments;
    }

    void arguments(StateID<ProcessArguments> arguments) {
      this.arguments = arguments;
      argumentsBuildStage = STAGE_INITIALIZED;
    }

    private byte processOutputBuildStage = STAGE_UNINITIALIZED;
    private StateID<ProcessOutput> processOutput;

    StateID<ProcessOutput> processOutput() {
      if (processOutputBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (processOutputBuildStage == STAGE_UNINITIALIZED) {
        processOutputBuildStage = STAGE_INITIALIZING;
        this.processOutput = Objects.requireNonNull(ImmutableMongodStarter.super.processOutput(), "processOutput");
        processOutputBuildStage = STAGE_INITIALIZED;
      }
      return this.processOutput;
    }

    void processOutput(StateID<ProcessOutput> processOutput) {
      this.processOutput = processOutput;
      processOutputBuildStage = STAGE_INITIALIZED;
    }

    private byte supportConfigBuildStage = STAGE_UNINITIALIZED;
    private StateID<SupportConfig> supportConfig;

    StateID<SupportConfig> supportConfig() {
      if (supportConfigBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (supportConfigBuildStage == STAGE_UNINITIALIZED) {
        supportConfigBuildStage = STAGE_INITIALIZING;
        this.supportConfig = Objects.requireNonNull(ImmutableMongodStarter.super.supportConfig(), "supportConfig");
        supportConfigBuildStage = STAGE_INITIALIZED;
      }
      return this.supportConfig;
    }

    void supportConfig(StateID<SupportConfig> supportConfig) {
      this.supportConfig = supportConfig;
      supportConfigBuildStage = STAGE_INITIALIZED;
    }

    private byte platformBuildStage = STAGE_UNINITIALIZED;
    private StateID<Platform> platform;

    StateID<Platform> platform() {
      if (platformBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (platformBuildStage == STAGE_UNINITIALIZED) {
        platformBuildStage = STAGE_INITIALIZING;
        this.platform = Objects.requireNonNull(ImmutableMongodStarter.super.platform(), "platform");
        platformBuildStage = STAGE_INITIALIZED;
      }
      return this.platform;
    }

    void platform(StateID<Platform> platform) {
      this.platform = platform;
      platformBuildStage = STAGE_INITIALIZED;
    }

    private byte netBuildStage = STAGE_UNINITIALIZED;
    private StateID<Net> net;

    StateID<Net> net() {
      if (netBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (netBuildStage == STAGE_UNINITIALIZED) {
        netBuildStage = STAGE_INITIALIZING;
        this.net = Objects.requireNonNull(ImmutableMongodStarter.super.net(), "net");
        netBuildStage = STAGE_INITIALIZED;
      }
      return this.net;
    }

    void net(StateID<Net> net) {
      this.net = net;
      netBuildStage = STAGE_INITIALIZED;
    }

    private byte transitionLabelBuildStage = STAGE_UNINITIALIZED;
    private String transitionLabel;

    String transitionLabel() {
      if (transitionLabelBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (transitionLabelBuildStage == STAGE_UNINITIALIZED) {
        transitionLabelBuildStage = STAGE_INITIALIZING;
        this.transitionLabel = Objects.requireNonNull(ImmutableMongodStarter.super.transitionLabel(), "transitionLabel");
        transitionLabelBuildStage = STAGE_INITIALIZED;
      }
      return this.transitionLabel;
    }

    void transitionLabel(String transitionLabel) {
      this.transitionLabel = transitionLabel;
      transitionLabelBuildStage = STAGE_INITIALIZED;
    }

    private byte destinationBuildStage = STAGE_UNINITIALIZED;
    private StateID<RunningMongodProcess> destination;

    StateID<RunningMongodProcess> destination() {
      if (destinationBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (destinationBuildStage == STAGE_UNINITIALIZED) {
        destinationBuildStage = STAGE_INITIALIZING;
        this.destination = Objects.requireNonNull(ImmutableMongodStarter.super.destination(), "destination");
        destinationBuildStage = STAGE_INITIALIZED;
      }
      return this.destination;
    }

    void destination(StateID<RunningMongodProcess> destination) {
      this.destination = destination;
      destinationBuildStage = STAGE_INITIALIZED;
    }

    private String formatInitCycleMessage() {
      List<String> attributes = new ArrayList<>();
      if (processExecutableBuildStage == STAGE_INITIALIZING) attributes.add("processExecutable");
      if (processConfigBuildStage == STAGE_INITIALIZING) attributes.add("processConfig");
      if (processEnvBuildStage == STAGE_INITIALIZING) attributes.add("processEnv");
      if (argumentsBuildStage == STAGE_INITIALIZING) attributes.add("arguments");
      if (processOutputBuildStage == STAGE_INITIALIZING) attributes.add("processOutput");
      if (supportConfigBuildStage == STAGE_INITIALIZING) attributes.add("supportConfig");
      if (platformBuildStage == STAGE_INITIALIZING) attributes.add("platform");
      if (netBuildStage == STAGE_INITIALIZING) attributes.add("net");
      if (transitionLabelBuildStage == STAGE_INITIALIZING) attributes.add("transitionLabel");
      if (destinationBuildStage == STAGE_INITIALIZING) attributes.add("destination");
      return "Cannot build MongodStarter, attribute initializers form cycle " + attributes;
    }
  }

  /**
   * @return The value of the {@code processExecutable} attribute
   */
  @Override
  public StateID<ExtractedFileSet> processExecutable() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.processExecutable()
        : this.processExecutable;
  }

  /**
   * @return The value of the {@code processConfig} attribute
   */
  @Override
  public StateID<ProcessConfig> processConfig() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.processConfig()
        : this.processConfig;
  }

  /**
   * @return The value of the {@code processEnv} attribute
   */
  @Override
  public StateID<ProcessEnv> processEnv() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.processEnv()
        : this.processEnv;
  }

  /**
   * @return The value of the {@code arguments} attribute
   */
  @Override
  public StateID<ProcessArguments> arguments() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.arguments()
        : this.arguments;
  }

  /**
   * @return The value of the {@code processOutput} attribute
   */
  @Override
  public StateID<ProcessOutput> processOutput() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.processOutput()
        : this.processOutput;
  }

  /**
   * @return The value of the {@code supportConfig} attribute
   */
  @Override
  public StateID<SupportConfig> supportConfig() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.supportConfig()
        : this.supportConfig;
  }

  /**
   * @return The value of the {@code platform} attribute
   */
  @Override
  public StateID<Platform> platform() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.platform()
        : this.platform;
  }

  /**
   * @return The value of the {@code net} attribute
   */
  @Override
  public StateID<Net> net() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.net()
        : this.net;
  }

  /**
   * @return The value of the {@code transitionLabel} attribute
   */
  @Override
  public String transitionLabel() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.transitionLabel()
        : this.transitionLabel;
  }

  /**
   * @return The value of the {@code destination} attribute
   */
  @Override
  public StateID<RunningMongodProcess> destination() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.destination()
        : this.destination;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link MongodStarter#processExecutable() processExecutable} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for processExecutable
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableMongodStarter withProcessExecutable(StateID<ExtractedFileSet> value) {
    if (this.processExecutable == value) return this;
    StateID<ExtractedFileSet> newValue = Objects.requireNonNull(value, "processExecutable");
    return new ImmutableMongodStarter(
        newValue,
        this.processConfig,
        this.processEnv,
        this.arguments,
        this.processOutput,
        this.supportConfig,
        this.platform,
        this.net,
        this.transitionLabel,
        this.destination);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link MongodStarter#processConfig() processConfig} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for processConfig
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableMongodStarter withProcessConfig(StateID<ProcessConfig> value) {
    if (this.processConfig == value) return this;
    StateID<ProcessConfig> newValue = Objects.requireNonNull(value, "processConfig");
    return new ImmutableMongodStarter(
        this.processExecutable,
        newValue,
        this.processEnv,
        this.arguments,
        this.processOutput,
        this.supportConfig,
        this.platform,
        this.net,
        this.transitionLabel,
        this.destination);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link MongodStarter#processEnv() processEnv} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for processEnv
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableMongodStarter withProcessEnv(StateID<ProcessEnv> value) {
    if (this.processEnv == value) return this;
    StateID<ProcessEnv> newValue = Objects.requireNonNull(value, "processEnv");
    return new ImmutableMongodStarter(
        this.processExecutable,
        this.processConfig,
        newValue,
        this.arguments,
        this.processOutput,
        this.supportConfig,
        this.platform,
        this.net,
        this.transitionLabel,
        this.destination);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link MongodStarter#arguments() arguments} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for arguments
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableMongodStarter withArguments(StateID<ProcessArguments> value) {
    if (this.arguments == value) return this;
    StateID<ProcessArguments> newValue = Objects.requireNonNull(value, "arguments");
    return new ImmutableMongodStarter(
        this.processExecutable,
        this.processConfig,
        this.processEnv,
        newValue,
        this.processOutput,
        this.supportConfig,
        this.platform,
        this.net,
        this.transitionLabel,
        this.destination);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link MongodStarter#processOutput() processOutput} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for processOutput
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableMongodStarter withProcessOutput(StateID<ProcessOutput> value) {
    if (this.processOutput == value) return this;
    StateID<ProcessOutput> newValue = Objects.requireNonNull(value, "processOutput");
    return new ImmutableMongodStarter(
        this.processExecutable,
        this.processConfig,
        this.processEnv,
        this.arguments,
        newValue,
        this.supportConfig,
        this.platform,
        this.net,
        this.transitionLabel,
        this.destination);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link MongodStarter#supportConfig() supportConfig} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for supportConfig
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableMongodStarter withSupportConfig(StateID<SupportConfig> value) {
    if (this.supportConfig == value) return this;
    StateID<SupportConfig> newValue = Objects.requireNonNull(value, "supportConfig");
    return new ImmutableMongodStarter(
        this.processExecutable,
        this.processConfig,
        this.processEnv,
        this.arguments,
        this.processOutput,
        newValue,
        this.platform,
        this.net,
        this.transitionLabel,
        this.destination);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link MongodStarter#platform() platform} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for platform
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableMongodStarter withPlatform(StateID<Platform> value) {
    if (this.platform == value) return this;
    StateID<Platform> newValue = Objects.requireNonNull(value, "platform");
    return new ImmutableMongodStarter(
        this.processExecutable,
        this.processConfig,
        this.processEnv,
        this.arguments,
        this.processOutput,
        this.supportConfig,
        newValue,
        this.net,
        this.transitionLabel,
        this.destination);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link MongodStarter#net() net} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for net
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableMongodStarter withNet(StateID<Net> value) {
    if (this.net == value) return this;
    StateID<Net> newValue = Objects.requireNonNull(value, "net");
    return new ImmutableMongodStarter(
        this.processExecutable,
        this.processConfig,
        this.processEnv,
        this.arguments,
        this.processOutput,
        this.supportConfig,
        this.platform,
        newValue,
        this.transitionLabel,
        this.destination);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link MongodStarter#transitionLabel() transitionLabel} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for transitionLabel
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableMongodStarter withTransitionLabel(String value) {
    String newValue = Objects.requireNonNull(value, "transitionLabel");
    if (this.transitionLabel.equals(newValue)) return this;
    return new ImmutableMongodStarter(
        this.processExecutable,
        this.processConfig,
        this.processEnv,
        this.arguments,
        this.processOutput,
        this.supportConfig,
        this.platform,
        this.net,
        newValue,
        this.destination);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link MongodStarter#destination() destination} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for destination
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableMongodStarter withDestination(StateID<RunningMongodProcess> value) {
    if (this.destination == value) return this;
    StateID<RunningMongodProcess> newValue = Objects.requireNonNull(value, "destination");
    return new ImmutableMongodStarter(
        this.processExecutable,
        this.processConfig,
        this.processEnv,
        this.arguments,
        this.processOutput,
        this.supportConfig,
        this.platform,
        this.net,
        this.transitionLabel,
        newValue);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableMongodStarter} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof ImmutableMongodStarter
        && equalTo(0, (ImmutableMongodStarter) another);
  }

  private boolean equalTo(int synthetic, ImmutableMongodStarter another) {
    return processExecutable.equals(another.processExecutable)
        && processConfig.equals(another.processConfig)
        && processEnv.equals(another.processEnv)
        && arguments.equals(another.arguments)
        && processOutput.equals(another.processOutput)
        && supportConfig.equals(another.supportConfig)
        && platform.equals(another.platform)
        && net.equals(another.net)
        && transitionLabel.equals(another.transitionLabel)
        && destination.equals(another.destination);
  }

  /**
   * Computes a hash code from attributes: {@code processExecutable}, {@code processConfig}, {@code processEnv}, {@code arguments}, {@code processOutput}, {@code supportConfig}, {@code platform}, {@code net}, {@code transitionLabel}, {@code destination}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + processExecutable.hashCode();
    h += (h << 5) + processConfig.hashCode();
    h += (h << 5) + processEnv.hashCode();
    h += (h << 5) + arguments.hashCode();
    h += (h << 5) + processOutput.hashCode();
    h += (h << 5) + supportConfig.hashCode();
    h += (h << 5) + platform.hashCode();
    h += (h << 5) + net.hashCode();
    h += (h << 5) + transitionLabel.hashCode();
    h += (h << 5) + destination.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code MongodStarter} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "MongodStarter{"
        + "processExecutable=" + processExecutable
        + ", processConfig=" + processConfig
        + ", processEnv=" + processEnv
        + ", arguments=" + arguments
        + ", processOutput=" + processOutput
        + ", supportConfig=" + supportConfig
        + ", platform=" + platform
        + ", net=" + net
        + ", transitionLabel=" + transitionLabel
        + ", destination=" + destination
        + "}";
  }

  /**
   * Creates an immutable copy of a {@link MongodStarter} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable MongodStarter instance
   */
  public static ImmutableMongodStarter copyOf(MongodStarter instance) {
    if (instance instanceof ImmutableMongodStarter) {
      return (ImmutableMongodStarter) instance;
    }
    return ImmutableMongodStarter.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableMongodStarter ImmutableMongodStarter}.
   * <pre>
   * ImmutableMongodStarter.builder()
   *    .processExecutable(de.flapdoodle.reverse.StateID&amp;lt;de.flapdoodle.embed.process.archives.ExtractedFileSet&amp;gt;) // optional {@link MongodStarter#processExecutable() processExecutable}
   *    .processConfig(de.flapdoodle.reverse.StateID&amp;lt;de.flapdoodle.embed.process.types.ProcessConfig&amp;gt;) // optional {@link MongodStarter#processConfig() processConfig}
   *    .processEnv(de.flapdoodle.reverse.StateID&amp;lt;de.flapdoodle.embed.process.types.ProcessEnv&amp;gt;) // optional {@link MongodStarter#processEnv() processEnv}
   *    .arguments(de.flapdoodle.reverse.StateID&amp;lt;de.flapdoodle.embed.process.types.ProcessArguments&amp;gt;) // optional {@link MongodStarter#arguments() arguments}
   *    .processOutput(de.flapdoodle.reverse.StateID&amp;lt;de.flapdoodle.embed.process.io.ProcessOutput&amp;gt;) // optional {@link MongodStarter#processOutput() processOutput}
   *    .supportConfig(de.flapdoodle.reverse.StateID&amp;lt;de.flapdoodle.embed.process.config.SupportConfig&amp;gt;) // optional {@link MongodStarter#supportConfig() supportConfig}
   *    .platform(de.flapdoodle.reverse.StateID&amp;lt;de.flapdoodle.os.Platform&amp;gt;) // optional {@link MongodStarter#platform() platform}
   *    .net(de.flapdoodle.reverse.StateID&amp;lt;de.flapdoodle.embed.mongo.config.Net&amp;gt;) // optional {@link MongodStarter#net() net}
   *    .transitionLabel(String) // optional {@link MongodStarter#transitionLabel() transitionLabel}
   *    .destination(de.flapdoodle.reverse.StateID&amp;lt;de.flapdoodle.embed.mongo.transitions.RunningMongodProcess&amp;gt;) // optional {@link MongodStarter#destination() destination}
   *    .build();
   * </pre>
   * @return A new ImmutableMongodStarter builder
   */
  public static ImmutableMongodStarter.Builder builder() {
    return new ImmutableMongodStarter.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableMongodStarter ImmutableMongodStarter}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  public static final class Builder {
    private StateID<ExtractedFileSet> processExecutable;
    private StateID<ProcessConfig> processConfig;
    private StateID<ProcessEnv> processEnv;
    private StateID<ProcessArguments> arguments;
    private StateID<ProcessOutput> processOutput;
    private StateID<SupportConfig> supportConfig;
    private StateID<Platform> platform;
    private StateID<Net> net;
    private String transitionLabel;
    private StateID<RunningMongodProcess> destination;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.flapdoodle.embed.mongo.transitions.MongodStarter} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(MongodStarter instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.flapdoodle.reverse.naming.HasLabel} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(HasLabel instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    private void from(Object object) {
      long bits = 0;
      if (object instanceof MongodStarter) {
        MongodStarter instance = (MongodStarter) object;
        supportConfig(instance.supportConfig());
        processExecutable(instance.processExecutable());
        if ((bits & 0x1L) == 0) {
          transitionLabel(instance.transitionLabel());
          bits |= 0x1L;
        }
        processConfig(instance.processConfig());
        processEnv(instance.processEnv());
        processOutput(instance.processOutput());
        destination(instance.destination());
        arguments(instance.arguments());
        net(instance.net());
        platform(instance.platform());
      }
      if (object instanceof HasLabel) {
        HasLabel instance = (HasLabel) object;
        if ((bits & 0x1L) == 0) {
          transitionLabel(instance.transitionLabel());
          bits |= 0x1L;
        }
      }
    }

    /**
     * Initializes the value for the {@link MongodStarter#processExecutable() processExecutable} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link MongodStarter#processExecutable() processExecutable}.</em>
     * @param processExecutable The value for processExecutable 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder processExecutable(StateID<ExtractedFileSet> processExecutable) {
      this.processExecutable = Objects.requireNonNull(processExecutable, "processExecutable");
      return this;
    }

    /**
     * Initializes the value for the {@link MongodStarter#processConfig() processConfig} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link MongodStarter#processConfig() processConfig}.</em>
     * @param processConfig The value for processConfig 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder processConfig(StateID<ProcessConfig> processConfig) {
      this.processConfig = Objects.requireNonNull(processConfig, "processConfig");
      return this;
    }

    /**
     * Initializes the value for the {@link MongodStarter#processEnv() processEnv} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link MongodStarter#processEnv() processEnv}.</em>
     * @param processEnv The value for processEnv 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder processEnv(StateID<ProcessEnv> processEnv) {
      this.processEnv = Objects.requireNonNull(processEnv, "processEnv");
      return this;
    }

    /**
     * Initializes the value for the {@link MongodStarter#arguments() arguments} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link MongodStarter#arguments() arguments}.</em>
     * @param arguments The value for arguments 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder arguments(StateID<ProcessArguments> arguments) {
      this.arguments = Objects.requireNonNull(arguments, "arguments");
      return this;
    }

    /**
     * Initializes the value for the {@link MongodStarter#processOutput() processOutput} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link MongodStarter#processOutput() processOutput}.</em>
     * @param processOutput The value for processOutput 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder processOutput(StateID<ProcessOutput> processOutput) {
      this.processOutput = Objects.requireNonNull(processOutput, "processOutput");
      return this;
    }

    /**
     * Initializes the value for the {@link MongodStarter#supportConfig() supportConfig} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link MongodStarter#supportConfig() supportConfig}.</em>
     * @param supportConfig The value for supportConfig 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder supportConfig(StateID<SupportConfig> supportConfig) {
      this.supportConfig = Objects.requireNonNull(supportConfig, "supportConfig");
      return this;
    }

    /**
     * Initializes the value for the {@link MongodStarter#platform() platform} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link MongodStarter#platform() platform}.</em>
     * @param platform The value for platform 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder platform(StateID<Platform> platform) {
      this.platform = Objects.requireNonNull(platform, "platform");
      return this;
    }

    /**
     * Initializes the value for the {@link MongodStarter#net() net} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link MongodStarter#net() net}.</em>
     * @param net The value for net 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder net(StateID<Net> net) {
      this.net = Objects.requireNonNull(net, "net");
      return this;
    }

    /**
     * Initializes the value for the {@link MongodStarter#transitionLabel() transitionLabel} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link MongodStarter#transitionLabel() transitionLabel}.</em>
     * @param transitionLabel The value for transitionLabel 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder transitionLabel(String transitionLabel) {
      this.transitionLabel = Objects.requireNonNull(transitionLabel, "transitionLabel");
      return this;
    }

    /**
     * Initializes the value for the {@link MongodStarter#destination() destination} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link MongodStarter#destination() destination}.</em>
     * @param destination The value for destination 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder destination(StateID<RunningMongodProcess> destination) {
      this.destination = Objects.requireNonNull(destination, "destination");
      return this;
    }

    /**
     * Builds a new {@link ImmutableMongodStarter ImmutableMongodStarter}.
     * @return An immutable instance of MongodStarter
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableMongodStarter build() {
      return new ImmutableMongodStarter(this);
    }
  }
}

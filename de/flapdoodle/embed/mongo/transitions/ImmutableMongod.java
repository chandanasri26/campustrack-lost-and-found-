package de.flapdoodle.embed.mongo.transitions;

import de.flapdoodle.embed.mongo.commands.MongodArguments;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.types.DatabaseDir;
import de.flapdoodle.embed.mongo.types.DistributionBaseUrl;
import de.flapdoodle.embed.mongo.types.SystemEnv;
import de.flapdoodle.embed.process.archives.ExtractedFileSet;
import de.flapdoodle.embed.process.config.SupportConfig;
import de.flapdoodle.embed.process.config.store.Package;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.embed.process.io.ProcessOutput;
import de.flapdoodle.embed.process.io.directories.PersistentDir;
import de.flapdoodle.embed.process.io.progress.ProgressListener;
import de.flapdoodle.embed.process.store.DownloadCache;
import de.flapdoodle.embed.process.store.ExtractedFileSetStore;
import de.flapdoodle.embed.process.transitions.DownloadPackage;
import de.flapdoodle.embed.process.transitions.InitTempDirectory;
import de.flapdoodle.embed.process.types.Name;
import de.flapdoodle.embed.process.types.ProcessConfig;
import de.flapdoodle.embed.process.types.ProcessEnv;
import de.flapdoodle.embed.process.types.ProcessWorkingDir;
import de.flapdoodle.os.Platform;
import de.flapdoodle.reverse.Transition;
import de.flapdoodle.reverse.transitions.ImmutableStart;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Immutable implementation of {@link Mongod}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableMongod.builder()}.
 */
@SuppressWarnings({"all"})
public final class ImmutableMongod extends Mongod {
  private final InitTempDirectory initTempDirectory;
  private final Transition<ProcessWorkingDir> processWorkingDir;
  private final Transition<DistributionBaseUrl> distributionBaseUrl;
  private final Transition<Platform> platform;
  private final Transition<Distribution> distribution;
  private final ImmutableStart<ProcessConfig> processConfig;
  private final Transition<ProcessEnv> processEnv;
  private final Transition<ProcessOutput> processOutput;
  private final Transition<SupportConfig> supportConfig;
  private final Transition<Name> commandName;
  private final Transition<SystemEnv> systemEnv;
  private final Transition<PersistentDir> persistentBaseDir;
  private final Transition<DownloadCache> downloadCache;
  private final Transition<ExtractedFileSetStore> extractedFileSetStore;
  private final DownloadPackage downloadPackage;
  private final Transition<ProgressListener> progressListener;
  private final Transition<ExtractedFileSet> extractPackage;
  private final Transition<Package> packageOfDistribution;
  private final Transition<MongodArguments> mongodArguments;
  private final Transition<Net> net;
  private final Transition<DatabaseDir> databaseDir;
  private final MongodProcessArguments mongodProcessArguments;

  private ImmutableMongod(ImmutableMongod.Builder builder) {
    if (builder.initTempDirectory != null) {
      initShim.initTempDirectory(builder.initTempDirectory);
    }
    if (builder.processWorkingDir != null) {
      initShim.processWorkingDir(builder.processWorkingDir);
    }
    if (builder.distributionBaseUrl != null) {
      initShim.distributionBaseUrl(builder.distributionBaseUrl);
    }
    if (builder.platform != null) {
      initShim.platform(builder.platform);
    }
    if (builder.distribution != null) {
      initShim.distribution(builder.distribution);
    }
    if (builder.processConfig != null) {
      initShim.processConfig(builder.processConfig);
    }
    if (builder.processEnv != null) {
      initShim.processEnv(builder.processEnv);
    }
    if (builder.processOutput != null) {
      initShim.processOutput(builder.processOutput);
    }
    if (builder.supportConfig != null) {
      initShim.supportConfig(builder.supportConfig);
    }
    if (builder.commandName != null) {
      initShim.commandName(builder.commandName);
    }
    if (builder.systemEnv != null) {
      initShim.systemEnv(builder.systemEnv);
    }
    if (builder.persistentBaseDir != null) {
      initShim.persistentBaseDir(builder.persistentBaseDir);
    }
    if (builder.downloadCache != null) {
      initShim.downloadCache(builder.downloadCache);
    }
    if (builder.extractedFileSetStore != null) {
      initShim.extractedFileSetStore(builder.extractedFileSetStore);
    }
    if (builder.downloadPackage != null) {
      initShim.downloadPackage(builder.downloadPackage);
    }
    if (builder.progressListener != null) {
      initShim.progressListener(builder.progressListener);
    }
    if (builder.extractPackage != null) {
      initShim.extractPackage(builder.extractPackage);
    }
    if (builder.packageOfDistribution != null) {
      initShim.packageOfDistribution(builder.packageOfDistribution);
    }
    if (builder.mongodArguments != null) {
      initShim.mongodArguments(builder.mongodArguments);
    }
    if (builder.net != null) {
      initShim.net(builder.net);
    }
    if (builder.databaseDir != null) {
      initShim.databaseDir(builder.databaseDir);
    }
    if (builder.mongodProcessArguments != null) {
      initShim.mongodProcessArguments(builder.mongodProcessArguments);
    }
    this.initTempDirectory = initShim.initTempDirectory();
    this.processWorkingDir = initShim.processWorkingDir();
    this.distributionBaseUrl = initShim.distributionBaseUrl();
    this.platform = initShim.platform();
    this.distribution = initShim.distribution();
    this.processConfig = initShim.processConfig();
    this.processEnv = initShim.processEnv();
    this.processOutput = initShim.processOutput();
    this.supportConfig = initShim.supportConfig();
    this.commandName = initShim.commandName();
    this.systemEnv = initShim.systemEnv();
    this.persistentBaseDir = initShim.persistentBaseDir();
    this.downloadCache = initShim.downloadCache();
    this.extractedFileSetStore = initShim.extractedFileSetStore();
    this.downloadPackage = initShim.downloadPackage();
    this.progressListener = initShim.progressListener();
    this.extractPackage = initShim.extractPackage();
    this.packageOfDistribution = initShim.packageOfDistribution();
    this.mongodArguments = initShim.mongodArguments();
    this.net = initShim.net();
    this.databaseDir = initShim.databaseDir();
    this.mongodProcessArguments = initShim.mongodProcessArguments();
    this.initShim = null;
  }

  private ImmutableMongod(
      InitTempDirectory initTempDirectory,
      Transition<ProcessWorkingDir> processWorkingDir,
      Transition<DistributionBaseUrl> distributionBaseUrl,
      Transition<Platform> platform,
      Transition<Distribution> distribution,
      ImmutableStart<ProcessConfig> processConfig,
      Transition<ProcessEnv> processEnv,
      Transition<ProcessOutput> processOutput,
      Transition<SupportConfig> supportConfig,
      Transition<Name> commandName,
      Transition<SystemEnv> systemEnv,
      Transition<PersistentDir> persistentBaseDir,
      Transition<DownloadCache> downloadCache,
      Transition<ExtractedFileSetStore> extractedFileSetStore,
      DownloadPackage downloadPackage,
      Transition<ProgressListener> progressListener,
      Transition<ExtractedFileSet> extractPackage,
      Transition<Package> packageOfDistribution,
      Transition<MongodArguments> mongodArguments,
      Transition<Net> net,
      Transition<DatabaseDir> databaseDir,
      MongodProcessArguments mongodProcessArguments) {
    this.initTempDirectory = initTempDirectory;
    this.processWorkingDir = processWorkingDir;
    this.distributionBaseUrl = distributionBaseUrl;
    this.platform = platform;
    this.distribution = distribution;
    this.processConfig = processConfig;
    this.processEnv = processEnv;
    this.processOutput = processOutput;
    this.supportConfig = supportConfig;
    this.commandName = commandName;
    this.systemEnv = systemEnv;
    this.persistentBaseDir = persistentBaseDir;
    this.downloadCache = downloadCache;
    this.extractedFileSetStore = extractedFileSetStore;
    this.downloadPackage = downloadPackage;
    this.progressListener = progressListener;
    this.extractPackage = extractPackage;
    this.packageOfDistribution = packageOfDistribution;
    this.mongodArguments = mongodArguments;
    this.net = net;
    this.databaseDir = databaseDir;
    this.mongodProcessArguments = mongodProcessArguments;
    this.initShim = null;
  }

  private static final byte STAGE_INITIALIZING = -1;
  private static final byte STAGE_UNINITIALIZED = 0;
  private static final byte STAGE_INITIALIZED = 1;
  private transient volatile InitShim initShim = new InitShim();

  private final class InitShim {
    private byte initTempDirectoryBuildStage = STAGE_UNINITIALIZED;
    private InitTempDirectory initTempDirectory;

    InitTempDirectory initTempDirectory() {
      if (initTempDirectoryBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (initTempDirectoryBuildStage == STAGE_UNINITIALIZED) {
        initTempDirectoryBuildStage = STAGE_INITIALIZING;
        this.initTempDirectory = Objects.requireNonNull(ImmutableMongod.super.initTempDirectory(), "initTempDirectory");
        initTempDirectoryBuildStage = STAGE_INITIALIZED;
      }
      return this.initTempDirectory;
    }

    void initTempDirectory(InitTempDirectory initTempDirectory) {
      this.initTempDirectory = initTempDirectory;
      initTempDirectoryBuildStage = STAGE_INITIALIZED;
    }

    private byte processWorkingDirBuildStage = STAGE_UNINITIALIZED;
    private Transition<ProcessWorkingDir> processWorkingDir;

    Transition<ProcessWorkingDir> processWorkingDir() {
      if (processWorkingDirBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (processWorkingDirBuildStage == STAGE_UNINITIALIZED) {
        processWorkingDirBuildStage = STAGE_INITIALIZING;
        this.processWorkingDir = Objects.requireNonNull(ImmutableMongod.super.processWorkingDir(), "processWorkingDir");
        processWorkingDirBuildStage = STAGE_INITIALIZED;
      }
      return this.processWorkingDir;
    }

    void processWorkingDir(Transition<ProcessWorkingDir> processWorkingDir) {
      this.processWorkingDir = processWorkingDir;
      processWorkingDirBuildStage = STAGE_INITIALIZED;
    }

    private byte distributionBaseUrlBuildStage = STAGE_UNINITIALIZED;
    private Transition<DistributionBaseUrl> distributionBaseUrl;

    Transition<DistributionBaseUrl> distributionBaseUrl() {
      if (distributionBaseUrlBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (distributionBaseUrlBuildStage == STAGE_UNINITIALIZED) {
        distributionBaseUrlBuildStage = STAGE_INITIALIZING;
        this.distributionBaseUrl = Objects.requireNonNull(ImmutableMongod.super.distributionBaseUrl(), "distributionBaseUrl");
        distributionBaseUrlBuildStage = STAGE_INITIALIZED;
      }
      return this.distributionBaseUrl;
    }

    void distributionBaseUrl(Transition<DistributionBaseUrl> distributionBaseUrl) {
      this.distributionBaseUrl = distributionBaseUrl;
      distributionBaseUrlBuildStage = STAGE_INITIALIZED;
    }

    private byte platformBuildStage = STAGE_UNINITIALIZED;
    private Transition<Platform> platform;

    Transition<Platform> platform() {
      if (platformBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (platformBuildStage == STAGE_UNINITIALIZED) {
        platformBuildStage = STAGE_INITIALIZING;
        this.platform = Objects.requireNonNull(ImmutableMongod.super.platform(), "platform");
        platformBuildStage = STAGE_INITIALIZED;
      }
      return this.platform;
    }

    void platform(Transition<Platform> platform) {
      this.platform = platform;
      platformBuildStage = STAGE_INITIALIZED;
    }

    private byte distributionBuildStage = STAGE_UNINITIALIZED;
    private Transition<Distribution> distribution;

    Transition<Distribution> distribution() {
      if (distributionBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (distributionBuildStage == STAGE_UNINITIALIZED) {
        distributionBuildStage = STAGE_INITIALIZING;
        this.distribution = Objects.requireNonNull(ImmutableMongod.super.distribution(), "distribution");
        distributionBuildStage = STAGE_INITIALIZED;
      }
      return this.distribution;
    }

    void distribution(Transition<Distribution> distribution) {
      this.distribution = distribution;
      distributionBuildStage = STAGE_INITIALIZED;
    }

    private byte processConfigBuildStage = STAGE_UNINITIALIZED;
    private ImmutableStart<ProcessConfig> processConfig;

    ImmutableStart<ProcessConfig> processConfig() {
      if (processConfigBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (processConfigBuildStage == STAGE_UNINITIALIZED) {
        processConfigBuildStage = STAGE_INITIALIZING;
        this.processConfig = Objects.requireNonNull(ImmutableMongod.super.processConfig(), "processConfig");
        processConfigBuildStage = STAGE_INITIALIZED;
      }
      return this.processConfig;
    }

    void processConfig(ImmutableStart<ProcessConfig> processConfig) {
      this.processConfig = processConfig;
      processConfigBuildStage = STAGE_INITIALIZED;
    }

    private byte processEnvBuildStage = STAGE_UNINITIALIZED;
    private Transition<ProcessEnv> processEnv;

    Transition<ProcessEnv> processEnv() {
      if (processEnvBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (processEnvBuildStage == STAGE_UNINITIALIZED) {
        processEnvBuildStage = STAGE_INITIALIZING;
        this.processEnv = Objects.requireNonNull(ImmutableMongod.super.processEnv(), "processEnv");
        processEnvBuildStage = STAGE_INITIALIZED;
      }
      return this.processEnv;
    }

    void processEnv(Transition<ProcessEnv> processEnv) {
      this.processEnv = processEnv;
      processEnvBuildStage = STAGE_INITIALIZED;
    }

    private byte processOutputBuildStage = STAGE_UNINITIALIZED;
    private Transition<ProcessOutput> processOutput;

    Transition<ProcessOutput> processOutput() {
      if (processOutputBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (processOutputBuildStage == STAGE_UNINITIALIZED) {
        processOutputBuildStage = STAGE_INITIALIZING;
        this.processOutput = Objects.requireNonNull(ImmutableMongod.super.processOutput(), "processOutput");
        processOutputBuildStage = STAGE_INITIALIZED;
      }
      return this.processOutput;
    }

    void processOutput(Transition<ProcessOutput> processOutput) {
      this.processOutput = processOutput;
      processOutputBuildStage = STAGE_INITIALIZED;
    }

    private byte supportConfigBuildStage = STAGE_UNINITIALIZED;
    private Transition<SupportConfig> supportConfig;

    Transition<SupportConfig> supportConfig() {
      if (supportConfigBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (supportConfigBuildStage == STAGE_UNINITIALIZED) {
        supportConfigBuildStage = STAGE_INITIALIZING;
        this.supportConfig = Objects.requireNonNull(ImmutableMongod.super.supportConfig(), "supportConfig");
        supportConfigBuildStage = STAGE_INITIALIZED;
      }
      return this.supportConfig;
    }

    void supportConfig(Transition<SupportConfig> supportConfig) {
      this.supportConfig = supportConfig;
      supportConfigBuildStage = STAGE_INITIALIZED;
    }

    private byte commandNameBuildStage = STAGE_UNINITIALIZED;
    private Transition<Name> commandName;

    Transition<Name> commandName() {
      if (commandNameBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (commandNameBuildStage == STAGE_UNINITIALIZED) {
        commandNameBuildStage = STAGE_INITIALIZING;
        this.commandName = Objects.requireNonNull(ImmutableMongod.super.commandName(), "commandName");
        commandNameBuildStage = STAGE_INITIALIZED;
      }
      return this.commandName;
    }

    void commandName(Transition<Name> commandName) {
      this.commandName = commandName;
      commandNameBuildStage = STAGE_INITIALIZED;
    }

    private byte systemEnvBuildStage = STAGE_UNINITIALIZED;
    private Transition<SystemEnv> systemEnv;

    Transition<SystemEnv> systemEnv() {
      if (systemEnvBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (systemEnvBuildStage == STAGE_UNINITIALIZED) {
        systemEnvBuildStage = STAGE_INITIALIZING;
        this.systemEnv = Objects.requireNonNull(ImmutableMongod.super.systemEnv(), "systemEnv");
        systemEnvBuildStage = STAGE_INITIALIZED;
      }
      return this.systemEnv;
    }

    void systemEnv(Transition<SystemEnv> systemEnv) {
      this.systemEnv = systemEnv;
      systemEnvBuildStage = STAGE_INITIALIZED;
    }

    private byte persistentBaseDirBuildStage = STAGE_UNINITIALIZED;
    private Transition<PersistentDir> persistentBaseDir;

    Transition<PersistentDir> persistentBaseDir() {
      if (persistentBaseDirBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (persistentBaseDirBuildStage == STAGE_UNINITIALIZED) {
        persistentBaseDirBuildStage = STAGE_INITIALIZING;
        this.persistentBaseDir = Objects.requireNonNull(ImmutableMongod.super.persistentBaseDir(), "persistentBaseDir");
        persistentBaseDirBuildStage = STAGE_INITIALIZED;
      }
      return this.persistentBaseDir;
    }

    void persistentBaseDir(Transition<PersistentDir> persistentBaseDir) {
      this.persistentBaseDir = persistentBaseDir;
      persistentBaseDirBuildStage = STAGE_INITIALIZED;
    }

    private byte downloadCacheBuildStage = STAGE_UNINITIALIZED;
    private Transition<DownloadCache> downloadCache;

    Transition<DownloadCache> downloadCache() {
      if (downloadCacheBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (downloadCacheBuildStage == STAGE_UNINITIALIZED) {
        downloadCacheBuildStage = STAGE_INITIALIZING;
        this.downloadCache = Objects.requireNonNull(ImmutableMongod.super.downloadCache(), "downloadCache");
        downloadCacheBuildStage = STAGE_INITIALIZED;
      }
      return this.downloadCache;
    }

    void downloadCache(Transition<DownloadCache> downloadCache) {
      this.downloadCache = downloadCache;
      downloadCacheBuildStage = STAGE_INITIALIZED;
    }

    private byte extractedFileSetStoreBuildStage = STAGE_UNINITIALIZED;
    private Transition<ExtractedFileSetStore> extractedFileSetStore;

    Transition<ExtractedFileSetStore> extractedFileSetStore() {
      if (extractedFileSetStoreBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (extractedFileSetStoreBuildStage == STAGE_UNINITIALIZED) {
        extractedFileSetStoreBuildStage = STAGE_INITIALIZING;
        this.extractedFileSetStore = Objects.requireNonNull(ImmutableMongod.super.extractedFileSetStore(), "extractedFileSetStore");
        extractedFileSetStoreBuildStage = STAGE_INITIALIZED;
      }
      return this.extractedFileSetStore;
    }

    void extractedFileSetStore(Transition<ExtractedFileSetStore> extractedFileSetStore) {
      this.extractedFileSetStore = extractedFileSetStore;
      extractedFileSetStoreBuildStage = STAGE_INITIALIZED;
    }

    private byte downloadPackageBuildStage = STAGE_UNINITIALIZED;
    private DownloadPackage downloadPackage;

    DownloadPackage downloadPackage() {
      if (downloadPackageBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (downloadPackageBuildStage == STAGE_UNINITIALIZED) {
        downloadPackageBuildStage = STAGE_INITIALIZING;
        this.downloadPackage = Objects.requireNonNull(ImmutableMongod.super.downloadPackage(), "downloadPackage");
        downloadPackageBuildStage = STAGE_INITIALIZED;
      }
      return this.downloadPackage;
    }

    void downloadPackage(DownloadPackage downloadPackage) {
      this.downloadPackage = downloadPackage;
      downloadPackageBuildStage = STAGE_INITIALIZED;
    }

    private byte progressListenerBuildStage = STAGE_UNINITIALIZED;
    private Transition<ProgressListener> progressListener;

    Transition<ProgressListener> progressListener() {
      if (progressListenerBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (progressListenerBuildStage == STAGE_UNINITIALIZED) {
        progressListenerBuildStage = STAGE_INITIALIZING;
        this.progressListener = Objects.requireNonNull(ImmutableMongod.super.progressListener(), "progressListener");
        progressListenerBuildStage = STAGE_INITIALIZED;
      }
      return this.progressListener;
    }

    void progressListener(Transition<ProgressListener> progressListener) {
      this.progressListener = progressListener;
      progressListenerBuildStage = STAGE_INITIALIZED;
    }

    private byte extractPackageBuildStage = STAGE_UNINITIALIZED;
    private Transition<ExtractedFileSet> extractPackage;

    Transition<ExtractedFileSet> extractPackage() {
      if (extractPackageBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (extractPackageBuildStage == STAGE_UNINITIALIZED) {
        extractPackageBuildStage = STAGE_INITIALIZING;
        this.extractPackage = Objects.requireNonNull(ImmutableMongod.super.extractPackage(), "extractPackage");
        extractPackageBuildStage = STAGE_INITIALIZED;
      }
      return this.extractPackage;
    }

    void extractPackage(Transition<ExtractedFileSet> extractPackage) {
      this.extractPackage = extractPackage;
      extractPackageBuildStage = STAGE_INITIALIZED;
    }

    private byte packageOfDistributionBuildStage = STAGE_UNINITIALIZED;
    private Transition<Package> packageOfDistribution;

    Transition<Package> packageOfDistribution() {
      if (packageOfDistributionBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (packageOfDistributionBuildStage == STAGE_UNINITIALIZED) {
        packageOfDistributionBuildStage = STAGE_INITIALIZING;
        this.packageOfDistribution = Objects.requireNonNull(ImmutableMongod.super.packageOfDistribution(), "packageOfDistribution");
        packageOfDistributionBuildStage = STAGE_INITIALIZED;
      }
      return this.packageOfDistribution;
    }

    void packageOfDistribution(Transition<Package> packageOfDistribution) {
      this.packageOfDistribution = packageOfDistribution;
      packageOfDistributionBuildStage = STAGE_INITIALIZED;
    }

    private byte mongodArgumentsBuildStage = STAGE_UNINITIALIZED;
    private Transition<MongodArguments> mongodArguments;

    Transition<MongodArguments> mongodArguments() {
      if (mongodArgumentsBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (mongodArgumentsBuildStage == STAGE_UNINITIALIZED) {
        mongodArgumentsBuildStage = STAGE_INITIALIZING;
        this.mongodArguments = Objects.requireNonNull(ImmutableMongod.super.mongodArguments(), "mongodArguments");
        mongodArgumentsBuildStage = STAGE_INITIALIZED;
      }
      return this.mongodArguments;
    }

    void mongodArguments(Transition<MongodArguments> mongodArguments) {
      this.mongodArguments = mongodArguments;
      mongodArgumentsBuildStage = STAGE_INITIALIZED;
    }

    private byte netBuildStage = STAGE_UNINITIALIZED;
    private Transition<Net> net;

    Transition<Net> net() {
      if (netBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (netBuildStage == STAGE_UNINITIALIZED) {
        netBuildStage = STAGE_INITIALIZING;
        this.net = Objects.requireNonNull(ImmutableMongod.super.net(), "net");
        netBuildStage = STAGE_INITIALIZED;
      }
      return this.net;
    }

    void net(Transition<Net> net) {
      this.net = net;
      netBuildStage = STAGE_INITIALIZED;
    }

    private byte databaseDirBuildStage = STAGE_UNINITIALIZED;
    private Transition<DatabaseDir> databaseDir;

    Transition<DatabaseDir> databaseDir() {
      if (databaseDirBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (databaseDirBuildStage == STAGE_UNINITIALIZED) {
        databaseDirBuildStage = STAGE_INITIALIZING;
        this.databaseDir = Objects.requireNonNull(ImmutableMongod.super.databaseDir(), "databaseDir");
        databaseDirBuildStage = STAGE_INITIALIZED;
      }
      return this.databaseDir;
    }

    void databaseDir(Transition<DatabaseDir> databaseDir) {
      this.databaseDir = databaseDir;
      databaseDirBuildStage = STAGE_INITIALIZED;
    }

    private byte mongodProcessArgumentsBuildStage = STAGE_UNINITIALIZED;
    private MongodProcessArguments mongodProcessArguments;

    MongodProcessArguments mongodProcessArguments() {
      if (mongodProcessArgumentsBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (mongodProcessArgumentsBuildStage == STAGE_UNINITIALIZED) {
        mongodProcessArgumentsBuildStage = STAGE_INITIALIZING;
        this.mongodProcessArguments = Objects.requireNonNull(ImmutableMongod.super.mongodProcessArguments(), "mongodProcessArguments");
        mongodProcessArgumentsBuildStage = STAGE_INITIALIZED;
      }
      return this.mongodProcessArguments;
    }

    void mongodProcessArguments(MongodProcessArguments mongodProcessArguments) {
      this.mongodProcessArguments = mongodProcessArguments;
      mongodProcessArgumentsBuildStage = STAGE_INITIALIZED;
    }

    private String formatInitCycleMessage() {
      List<String> attributes = new ArrayList<>();
      if (initTempDirectoryBuildStage == STAGE_INITIALIZING) attributes.add("initTempDirectory");
      if (processWorkingDirBuildStage == STAGE_INITIALIZING) attributes.add("processWorkingDir");
      if (distributionBaseUrlBuildStage == STAGE_INITIALIZING) attributes.add("distributionBaseUrl");
      if (platformBuildStage == STAGE_INITIALIZING) attributes.add("platform");
      if (distributionBuildStage == STAGE_INITIALIZING) attributes.add("distribution");
      if (processConfigBuildStage == STAGE_INITIALIZING) attributes.add("processConfig");
      if (processEnvBuildStage == STAGE_INITIALIZING) attributes.add("processEnv");
      if (processOutputBuildStage == STAGE_INITIALIZING) attributes.add("processOutput");
      if (supportConfigBuildStage == STAGE_INITIALIZING) attributes.add("supportConfig");
      if (commandNameBuildStage == STAGE_INITIALIZING) attributes.add("commandName");
      if (systemEnvBuildStage == STAGE_INITIALIZING) attributes.add("systemEnv");
      if (persistentBaseDirBuildStage == STAGE_INITIALIZING) attributes.add("persistentBaseDir");
      if (downloadCacheBuildStage == STAGE_INITIALIZING) attributes.add("downloadCache");
      if (extractedFileSetStoreBuildStage == STAGE_INITIALIZING) attributes.add("extractedFileSetStore");
      if (downloadPackageBuildStage == STAGE_INITIALIZING) attributes.add("downloadPackage");
      if (progressListenerBuildStage == STAGE_INITIALIZING) attributes.add("progressListener");
      if (extractPackageBuildStage == STAGE_INITIALIZING) attributes.add("extractPackage");
      if (packageOfDistributionBuildStage == STAGE_INITIALIZING) attributes.add("packageOfDistribution");
      if (mongodArgumentsBuildStage == STAGE_INITIALIZING) attributes.add("mongodArguments");
      if (netBuildStage == STAGE_INITIALIZING) attributes.add("net");
      if (databaseDirBuildStage == STAGE_INITIALIZING) attributes.add("databaseDir");
      if (mongodProcessArgumentsBuildStage == STAGE_INITIALIZING) attributes.add("mongodProcessArguments");
      return "Cannot build Mongod, attribute initializers form cycle " + attributes;
    }
  }

  /**
   * @return The value of the {@code initTempDirectory} attribute
   */
  @Override
  public InitTempDirectory initTempDirectory() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.initTempDirectory()
        : this.initTempDirectory;
  }

  /**
   * @return The value of the {@code processWorkingDir} attribute
   */
  @Override
  public Transition<ProcessWorkingDir> processWorkingDir() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.processWorkingDir()
        : this.processWorkingDir;
  }

  /**
   * @return The value of the {@code distributionBaseUrl} attribute
   */
  @Override
  public Transition<DistributionBaseUrl> distributionBaseUrl() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.distributionBaseUrl()
        : this.distributionBaseUrl;
  }

  /**
   * @return The value of the {@code platform} attribute
   */
  @Override
  public Transition<Platform> platform() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.platform()
        : this.platform;
  }

  /**
   * @return The value of the {@code distribution} attribute
   */
  @Override
  public Transition<Distribution> distribution() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.distribution()
        : this.distribution;
  }

  /**
   * @return The value of the {@code processConfig} attribute
   */
  @Override
  public ImmutableStart<ProcessConfig> processConfig() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.processConfig()
        : this.processConfig;
  }

  /**
   * @return The value of the {@code processEnv} attribute
   */
  @Override
  public Transition<ProcessEnv> processEnv() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.processEnv()
        : this.processEnv;
  }

  /**
   * @return The value of the {@code processOutput} attribute
   */
  @Override
  public Transition<ProcessOutput> processOutput() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.processOutput()
        : this.processOutput;
  }

  /**
   * @return The value of the {@code supportConfig} attribute
   */
  @Override
  public Transition<SupportConfig> supportConfig() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.supportConfig()
        : this.supportConfig;
  }

  /**
   * @return The value of the {@code commandName} attribute
   */
  @Override
  public Transition<Name> commandName() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.commandName()
        : this.commandName;
  }

  /**
   * @return The value of the {@code systemEnv} attribute
   */
  @Override
  public Transition<SystemEnv> systemEnv() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.systemEnv()
        : this.systemEnv;
  }

  /**
   * @return The value of the {@code persistentBaseDir} attribute
   */
  @Override
  public Transition<PersistentDir> persistentBaseDir() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.persistentBaseDir()
        : this.persistentBaseDir;
  }

  /**
   * @return The value of the {@code downloadCache} attribute
   */
  @Override
  public Transition<DownloadCache> downloadCache() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.downloadCache()
        : this.downloadCache;
  }

  /**
   * @return The value of the {@code extractedFileSetStore} attribute
   */
  @Override
  public Transition<ExtractedFileSetStore> extractedFileSetStore() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.extractedFileSetStore()
        : this.extractedFileSetStore;
  }

  /**
   * @return The value of the {@code downloadPackage} attribute
   */
  @Override
  public DownloadPackage downloadPackage() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.downloadPackage()
        : this.downloadPackage;
  }

  /**
   * @return The value of the {@code progressListener} attribute
   */
  @Override
  public Transition<ProgressListener> progressListener() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.progressListener()
        : this.progressListener;
  }

  /**
   * @return The value of the {@code extractPackage} attribute
   */
  @Override
  public Transition<ExtractedFileSet> extractPackage() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.extractPackage()
        : this.extractPackage;
  }

  /**
   * @return The value of the {@code packageOfDistribution} attribute
   */
  @Override
  public Transition<Package> packageOfDistribution() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.packageOfDistribution()
        : this.packageOfDistribution;
  }

  /**
   * @return The value of the {@code mongodArguments} attribute
   */
  @Override
  public Transition<MongodArguments> mongodArguments() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.mongodArguments()
        : this.mongodArguments;
  }

  /**
   * @return The value of the {@code net} attribute
   */
  @Override
  public Transition<Net> net() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.net()
        : this.net;
  }

  /**
   * @return The value of the {@code databaseDir} attribute
   */
  @Override
  public Transition<DatabaseDir> databaseDir() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.databaseDir()
        : this.databaseDir;
  }

  /**
   * @return The value of the {@code mongodProcessArguments} attribute
   */
  @Override
  public MongodProcessArguments mongodProcessArguments() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.mongodProcessArguments()
        : this.mongodProcessArguments;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Mongod#initTempDirectory() initTempDirectory} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for initTempDirectory
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableMongod withInitTempDirectory(InitTempDirectory value) {
    if (this.initTempDirectory == value) return this;
    InitTempDirectory newValue = Objects.requireNonNull(value, "initTempDirectory");
    return new ImmutableMongod(
        newValue,
        this.processWorkingDir,
        this.distributionBaseUrl,
        this.platform,
        this.distribution,
        this.processConfig,
        this.processEnv,
        this.processOutput,
        this.supportConfig,
        this.commandName,
        this.systemEnv,
        this.persistentBaseDir,
        this.downloadCache,
        this.extractedFileSetStore,
        this.downloadPackage,
        this.progressListener,
        this.extractPackage,
        this.packageOfDistribution,
        this.mongodArguments,
        this.net,
        this.databaseDir,
        this.mongodProcessArguments);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Mongod#processWorkingDir() processWorkingDir} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for processWorkingDir
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableMongod withProcessWorkingDir(Transition<ProcessWorkingDir> value) {
    if (this.processWorkingDir == value) return this;
    Transition<ProcessWorkingDir> newValue = Objects.requireNonNull(value, "processWorkingDir");
    return new ImmutableMongod(
        this.initTempDirectory,
        newValue,
        this.distributionBaseUrl,
        this.platform,
        this.distribution,
        this.processConfig,
        this.processEnv,
        this.processOutput,
        this.supportConfig,
        this.commandName,
        this.systemEnv,
        this.persistentBaseDir,
        this.downloadCache,
        this.extractedFileSetStore,
        this.downloadPackage,
        this.progressListener,
        this.extractPackage,
        this.packageOfDistribution,
        this.mongodArguments,
        this.net,
        this.databaseDir,
        this.mongodProcessArguments);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Mongod#distributionBaseUrl() distributionBaseUrl} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for distributionBaseUrl
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableMongod withDistributionBaseUrl(Transition<DistributionBaseUrl> value) {
    if (this.distributionBaseUrl == value) return this;
    Transition<DistributionBaseUrl> newValue = Objects.requireNonNull(value, "distributionBaseUrl");
    return new ImmutableMongod(
        this.initTempDirectory,
        this.processWorkingDir,
        newValue,
        this.platform,
        this.distribution,
        this.processConfig,
        this.processEnv,
        this.processOutput,
        this.supportConfig,
        this.commandName,
        this.systemEnv,
        this.persistentBaseDir,
        this.downloadCache,
        this.extractedFileSetStore,
        this.downloadPackage,
        this.progressListener,
        this.extractPackage,
        this.packageOfDistribution,
        this.mongodArguments,
        this.net,
        this.databaseDir,
        this.mongodProcessArguments);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Mongod#platform() platform} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for platform
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableMongod withPlatform(Transition<Platform> value) {
    if (this.platform == value) return this;
    Transition<Platform> newValue = Objects.requireNonNull(value, "platform");
    return new ImmutableMongod(
        this.initTempDirectory,
        this.processWorkingDir,
        this.distributionBaseUrl,
        newValue,
        this.distribution,
        this.processConfig,
        this.processEnv,
        this.processOutput,
        this.supportConfig,
        this.commandName,
        this.systemEnv,
        this.persistentBaseDir,
        this.downloadCache,
        this.extractedFileSetStore,
        this.downloadPackage,
        this.progressListener,
        this.extractPackage,
        this.packageOfDistribution,
        this.mongodArguments,
        this.net,
        this.databaseDir,
        this.mongodProcessArguments);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Mongod#distribution() distribution} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for distribution
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableMongod withDistribution(Transition<Distribution> value) {
    if (this.distribution == value) return this;
    Transition<Distribution> newValue = Objects.requireNonNull(value, "distribution");
    return new ImmutableMongod(
        this.initTempDirectory,
        this.processWorkingDir,
        this.distributionBaseUrl,
        this.platform,
        newValue,
        this.processConfig,
        this.processEnv,
        this.processOutput,
        this.supportConfig,
        this.commandName,
        this.systemEnv,
        this.persistentBaseDir,
        this.downloadCache,
        this.extractedFileSetStore,
        this.downloadPackage,
        this.progressListener,
        this.extractPackage,
        this.packageOfDistribution,
        this.mongodArguments,
        this.net,
        this.databaseDir,
        this.mongodProcessArguments);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Mongod#processConfig() processConfig} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for processConfig
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableMongod withProcessConfig(ImmutableStart<ProcessConfig> value) {
    if (this.processConfig == value) return this;
    ImmutableStart<ProcessConfig> newValue = Objects.requireNonNull(value, "processConfig");
    return new ImmutableMongod(
        this.initTempDirectory,
        this.processWorkingDir,
        this.distributionBaseUrl,
        this.platform,
        this.distribution,
        newValue,
        this.processEnv,
        this.processOutput,
        this.supportConfig,
        this.commandName,
        this.systemEnv,
        this.persistentBaseDir,
        this.downloadCache,
        this.extractedFileSetStore,
        this.downloadPackage,
        this.progressListener,
        this.extractPackage,
        this.packageOfDistribution,
        this.mongodArguments,
        this.net,
        this.databaseDir,
        this.mongodProcessArguments);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Mongod#processEnv() processEnv} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for processEnv
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableMongod withProcessEnv(Transition<ProcessEnv> value) {
    if (this.processEnv == value) return this;
    Transition<ProcessEnv> newValue = Objects.requireNonNull(value, "processEnv");
    return new ImmutableMongod(
        this.initTempDirectory,
        this.processWorkingDir,
        this.distributionBaseUrl,
        this.platform,
        this.distribution,
        this.processConfig,
        newValue,
        this.processOutput,
        this.supportConfig,
        this.commandName,
        this.systemEnv,
        this.persistentBaseDir,
        this.downloadCache,
        this.extractedFileSetStore,
        this.downloadPackage,
        this.progressListener,
        this.extractPackage,
        this.packageOfDistribution,
        this.mongodArguments,
        this.net,
        this.databaseDir,
        this.mongodProcessArguments);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Mongod#processOutput() processOutput} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for processOutput
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableMongod withProcessOutput(Transition<ProcessOutput> value) {
    if (this.processOutput == value) return this;
    Transition<ProcessOutput> newValue = Objects.requireNonNull(value, "processOutput");
    return new ImmutableMongod(
        this.initTempDirectory,
        this.processWorkingDir,
        this.distributionBaseUrl,
        this.platform,
        this.distribution,
        this.processConfig,
        this.processEnv,
        newValue,
        this.supportConfig,
        this.commandName,
        this.systemEnv,
        this.persistentBaseDir,
        this.downloadCache,
        this.extractedFileSetStore,
        this.downloadPackage,
        this.progressListener,
        this.extractPackage,
        this.packageOfDistribution,
        this.mongodArguments,
        this.net,
        this.databaseDir,
        this.mongodProcessArguments);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Mongod#supportConfig() supportConfig} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for supportConfig
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableMongod withSupportConfig(Transition<SupportConfig> value) {
    if (this.supportConfig == value) return this;
    Transition<SupportConfig> newValue = Objects.requireNonNull(value, "supportConfig");
    return new ImmutableMongod(
        this.initTempDirectory,
        this.processWorkingDir,
        this.distributionBaseUrl,
        this.platform,
        this.distribution,
        this.processConfig,
        this.processEnv,
        this.processOutput,
        newValue,
        this.commandName,
        this.systemEnv,
        this.persistentBaseDir,
        this.downloadCache,
        this.extractedFileSetStore,
        this.downloadPackage,
        this.progressListener,
        this.extractPackage,
        this.packageOfDistribution,
        this.mongodArguments,
        this.net,
        this.databaseDir,
        this.mongodProcessArguments);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Mongod#commandName() commandName} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for commandName
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableMongod withCommandName(Transition<Name> value) {
    if (this.commandName == value) return this;
    Transition<Name> newValue = Objects.requireNonNull(value, "commandName");
    return new ImmutableMongod(
        this.initTempDirectory,
        this.processWorkingDir,
        this.distributionBaseUrl,
        this.platform,
        this.distribution,
        this.processConfig,
        this.processEnv,
        this.processOutput,
        this.supportConfig,
        newValue,
        this.systemEnv,
        this.persistentBaseDir,
        this.downloadCache,
        this.extractedFileSetStore,
        this.downloadPackage,
        this.progressListener,
        this.extractPackage,
        this.packageOfDistribution,
        this.mongodArguments,
        this.net,
        this.databaseDir,
        this.mongodProcessArguments);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Mongod#systemEnv() systemEnv} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for systemEnv
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableMongod withSystemEnv(Transition<SystemEnv> value) {
    if (this.systemEnv == value) return this;
    Transition<SystemEnv> newValue = Objects.requireNonNull(value, "systemEnv");
    return new ImmutableMongod(
        this.initTempDirectory,
        this.processWorkingDir,
        this.distributionBaseUrl,
        this.platform,
        this.distribution,
        this.processConfig,
        this.processEnv,
        this.processOutput,
        this.supportConfig,
        this.commandName,
        newValue,
        this.persistentBaseDir,
        this.downloadCache,
        this.extractedFileSetStore,
        this.downloadPackage,
        this.progressListener,
        this.extractPackage,
        this.packageOfDistribution,
        this.mongodArguments,
        this.net,
        this.databaseDir,
        this.mongodProcessArguments);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Mongod#persistentBaseDir() persistentBaseDir} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for persistentBaseDir
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableMongod withPersistentBaseDir(Transition<PersistentDir> value) {
    if (this.persistentBaseDir == value) return this;
    Transition<PersistentDir> newValue = Objects.requireNonNull(value, "persistentBaseDir");
    return new ImmutableMongod(
        this.initTempDirectory,
        this.processWorkingDir,
        this.distributionBaseUrl,
        this.platform,
        this.distribution,
        this.processConfig,
        this.processEnv,
        this.processOutput,
        this.supportConfig,
        this.commandName,
        this.systemEnv,
        newValue,
        this.downloadCache,
        this.extractedFileSetStore,
        this.downloadPackage,
        this.progressListener,
        this.extractPackage,
        this.packageOfDistribution,
        this.mongodArguments,
        this.net,
        this.databaseDir,
        this.mongodProcessArguments);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Mongod#downloadCache() downloadCache} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for downloadCache
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableMongod withDownloadCache(Transition<DownloadCache> value) {
    if (this.downloadCache == value) return this;
    Transition<DownloadCache> newValue = Objects.requireNonNull(value, "downloadCache");
    return new ImmutableMongod(
        this.initTempDirectory,
        this.processWorkingDir,
        this.distributionBaseUrl,
        this.platform,
        this.distribution,
        this.processConfig,
        this.processEnv,
        this.processOutput,
        this.supportConfig,
        this.commandName,
        this.systemEnv,
        this.persistentBaseDir,
        newValue,
        this.extractedFileSetStore,
        this.downloadPackage,
        this.progressListener,
        this.extractPackage,
        this.packageOfDistribution,
        this.mongodArguments,
        this.net,
        this.databaseDir,
        this.mongodProcessArguments);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Mongod#extractedFileSetStore() extractedFileSetStore} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for extractedFileSetStore
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableMongod withExtractedFileSetStore(Transition<ExtractedFileSetStore> value) {
    if (this.extractedFileSetStore == value) return this;
    Transition<ExtractedFileSetStore> newValue = Objects.requireNonNull(value, "extractedFileSetStore");
    return new ImmutableMongod(
        this.initTempDirectory,
        this.processWorkingDir,
        this.distributionBaseUrl,
        this.platform,
        this.distribution,
        this.processConfig,
        this.processEnv,
        this.processOutput,
        this.supportConfig,
        this.commandName,
        this.systemEnv,
        this.persistentBaseDir,
        this.downloadCache,
        newValue,
        this.downloadPackage,
        this.progressListener,
        this.extractPackage,
        this.packageOfDistribution,
        this.mongodArguments,
        this.net,
        this.databaseDir,
        this.mongodProcessArguments);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Mongod#downloadPackage() downloadPackage} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for downloadPackage
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableMongod withDownloadPackage(DownloadPackage value) {
    if (this.downloadPackage == value) return this;
    DownloadPackage newValue = Objects.requireNonNull(value, "downloadPackage");
    return new ImmutableMongod(
        this.initTempDirectory,
        this.processWorkingDir,
        this.distributionBaseUrl,
        this.platform,
        this.distribution,
        this.processConfig,
        this.processEnv,
        this.processOutput,
        this.supportConfig,
        this.commandName,
        this.systemEnv,
        this.persistentBaseDir,
        this.downloadCache,
        this.extractedFileSetStore,
        newValue,
        this.progressListener,
        this.extractPackage,
        this.packageOfDistribution,
        this.mongodArguments,
        this.net,
        this.databaseDir,
        this.mongodProcessArguments);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Mongod#progressListener() progressListener} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for progressListener
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableMongod withProgressListener(Transition<ProgressListener> value) {
    if (this.progressListener == value) return this;
    Transition<ProgressListener> newValue = Objects.requireNonNull(value, "progressListener");
    return new ImmutableMongod(
        this.initTempDirectory,
        this.processWorkingDir,
        this.distributionBaseUrl,
        this.platform,
        this.distribution,
        this.processConfig,
        this.processEnv,
        this.processOutput,
        this.supportConfig,
        this.commandName,
        this.systemEnv,
        this.persistentBaseDir,
        this.downloadCache,
        this.extractedFileSetStore,
        this.downloadPackage,
        newValue,
        this.extractPackage,
        this.packageOfDistribution,
        this.mongodArguments,
        this.net,
        this.databaseDir,
        this.mongodProcessArguments);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Mongod#extractPackage() extractPackage} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for extractPackage
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableMongod withExtractPackage(Transition<ExtractedFileSet> value) {
    if (this.extractPackage == value) return this;
    Transition<ExtractedFileSet> newValue = Objects.requireNonNull(value, "extractPackage");
    return new ImmutableMongod(
        this.initTempDirectory,
        this.processWorkingDir,
        this.distributionBaseUrl,
        this.platform,
        this.distribution,
        this.processConfig,
        this.processEnv,
        this.processOutput,
        this.supportConfig,
        this.commandName,
        this.systemEnv,
        this.persistentBaseDir,
        this.downloadCache,
        this.extractedFileSetStore,
        this.downloadPackage,
        this.progressListener,
        newValue,
        this.packageOfDistribution,
        this.mongodArguments,
        this.net,
        this.databaseDir,
        this.mongodProcessArguments);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Mongod#packageOfDistribution() packageOfDistribution} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for packageOfDistribution
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableMongod withPackageOfDistribution(Transition<Package> value) {
    if (this.packageOfDistribution == value) return this;
    Transition<Package> newValue = Objects.requireNonNull(value, "packageOfDistribution");
    return new ImmutableMongod(
        this.initTempDirectory,
        this.processWorkingDir,
        this.distributionBaseUrl,
        this.platform,
        this.distribution,
        this.processConfig,
        this.processEnv,
        this.processOutput,
        this.supportConfig,
        this.commandName,
        this.systemEnv,
        this.persistentBaseDir,
        this.downloadCache,
        this.extractedFileSetStore,
        this.downloadPackage,
        this.progressListener,
        this.extractPackage,
        newValue,
        this.mongodArguments,
        this.net,
        this.databaseDir,
        this.mongodProcessArguments);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Mongod#mongodArguments() mongodArguments} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for mongodArguments
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableMongod withMongodArguments(Transition<MongodArguments> value) {
    if (this.mongodArguments == value) return this;
    Transition<MongodArguments> newValue = Objects.requireNonNull(value, "mongodArguments");
    return new ImmutableMongod(
        this.initTempDirectory,
        this.processWorkingDir,
        this.distributionBaseUrl,
        this.platform,
        this.distribution,
        this.processConfig,
        this.processEnv,
        this.processOutput,
        this.supportConfig,
        this.commandName,
        this.systemEnv,
        this.persistentBaseDir,
        this.downloadCache,
        this.extractedFileSetStore,
        this.downloadPackage,
        this.progressListener,
        this.extractPackage,
        this.packageOfDistribution,
        newValue,
        this.net,
        this.databaseDir,
        this.mongodProcessArguments);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Mongod#net() net} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for net
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableMongod withNet(Transition<Net> value) {
    if (this.net == value) return this;
    Transition<Net> newValue = Objects.requireNonNull(value, "net");
    return new ImmutableMongod(
        this.initTempDirectory,
        this.processWorkingDir,
        this.distributionBaseUrl,
        this.platform,
        this.distribution,
        this.processConfig,
        this.processEnv,
        this.processOutput,
        this.supportConfig,
        this.commandName,
        this.systemEnv,
        this.persistentBaseDir,
        this.downloadCache,
        this.extractedFileSetStore,
        this.downloadPackage,
        this.progressListener,
        this.extractPackage,
        this.packageOfDistribution,
        this.mongodArguments,
        newValue,
        this.databaseDir,
        this.mongodProcessArguments);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Mongod#databaseDir() databaseDir} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for databaseDir
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableMongod withDatabaseDir(Transition<DatabaseDir> value) {
    if (this.databaseDir == value) return this;
    Transition<DatabaseDir> newValue = Objects.requireNonNull(value, "databaseDir");
    return new ImmutableMongod(
        this.initTempDirectory,
        this.processWorkingDir,
        this.distributionBaseUrl,
        this.platform,
        this.distribution,
        this.processConfig,
        this.processEnv,
        this.processOutput,
        this.supportConfig,
        this.commandName,
        this.systemEnv,
        this.persistentBaseDir,
        this.downloadCache,
        this.extractedFileSetStore,
        this.downloadPackage,
        this.progressListener,
        this.extractPackage,
        this.packageOfDistribution,
        this.mongodArguments,
        this.net,
        newValue,
        this.mongodProcessArguments);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Mongod#mongodProcessArguments() mongodProcessArguments} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for mongodProcessArguments
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableMongod withMongodProcessArguments(MongodProcessArguments value) {
    if (this.mongodProcessArguments == value) return this;
    MongodProcessArguments newValue = Objects.requireNonNull(value, "mongodProcessArguments");
    return new ImmutableMongod(
        this.initTempDirectory,
        this.processWorkingDir,
        this.distributionBaseUrl,
        this.platform,
        this.distribution,
        this.processConfig,
        this.processEnv,
        this.processOutput,
        this.supportConfig,
        this.commandName,
        this.systemEnv,
        this.persistentBaseDir,
        this.downloadCache,
        this.extractedFileSetStore,
        this.downloadPackage,
        this.progressListener,
        this.extractPackage,
        this.packageOfDistribution,
        this.mongodArguments,
        this.net,
        this.databaseDir,
        newValue);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableMongod} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof ImmutableMongod
        && equalTo(0, (ImmutableMongod) another);
  }

  private boolean equalTo(int synthetic, ImmutableMongod another) {
    return initTempDirectory.equals(another.initTempDirectory)
        && processWorkingDir.equals(another.processWorkingDir)
        && distributionBaseUrl.equals(another.distributionBaseUrl)
        && platform.equals(another.platform)
        && distribution.equals(another.distribution)
        && processConfig.equals(another.processConfig)
        && processEnv.equals(another.processEnv)
        && processOutput.equals(another.processOutput)
        && supportConfig.equals(another.supportConfig)
        && commandName.equals(another.commandName)
        && systemEnv.equals(another.systemEnv)
        && persistentBaseDir.equals(another.persistentBaseDir)
        && downloadCache.equals(another.downloadCache)
        && extractedFileSetStore.equals(another.extractedFileSetStore)
        && downloadPackage.equals(another.downloadPackage)
        && progressListener.equals(another.progressListener)
        && extractPackage.equals(another.extractPackage)
        && packageOfDistribution.equals(another.packageOfDistribution)
        && mongodArguments.equals(another.mongodArguments)
        && net.equals(another.net)
        && databaseDir.equals(another.databaseDir)
        && mongodProcessArguments.equals(another.mongodProcessArguments);
  }

  /**
   * Computes a hash code from attributes: {@code initTempDirectory}, {@code processWorkingDir}, {@code distributionBaseUrl}, {@code platform}, {@code distribution}, {@code processConfig}, {@code processEnv}, {@code processOutput}, {@code supportConfig}, {@code commandName}, {@code systemEnv}, {@code persistentBaseDir}, {@code downloadCache}, {@code extractedFileSetStore}, {@code downloadPackage}, {@code progressListener}, {@code extractPackage}, {@code packageOfDistribution}, {@code mongodArguments}, {@code net}, {@code databaseDir}, {@code mongodProcessArguments}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + initTempDirectory.hashCode();
    h += (h << 5) + processWorkingDir.hashCode();
    h += (h << 5) + distributionBaseUrl.hashCode();
    h += (h << 5) + platform.hashCode();
    h += (h << 5) + distribution.hashCode();
    h += (h << 5) + processConfig.hashCode();
    h += (h << 5) + processEnv.hashCode();
    h += (h << 5) + processOutput.hashCode();
    h += (h << 5) + supportConfig.hashCode();
    h += (h << 5) + commandName.hashCode();
    h += (h << 5) + systemEnv.hashCode();
    h += (h << 5) + persistentBaseDir.hashCode();
    h += (h << 5) + downloadCache.hashCode();
    h += (h << 5) + extractedFileSetStore.hashCode();
    h += (h << 5) + downloadPackage.hashCode();
    h += (h << 5) + progressListener.hashCode();
    h += (h << 5) + extractPackage.hashCode();
    h += (h << 5) + packageOfDistribution.hashCode();
    h += (h << 5) + mongodArguments.hashCode();
    h += (h << 5) + net.hashCode();
    h += (h << 5) + databaseDir.hashCode();
    h += (h << 5) + mongodProcessArguments.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code Mongod} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "Mongod{"
        + "initTempDirectory=" + initTempDirectory
        + ", processWorkingDir=" + processWorkingDir
        + ", distributionBaseUrl=" + distributionBaseUrl
        + ", platform=" + platform
        + ", distribution=" + distribution
        + ", processConfig=" + processConfig
        + ", processEnv=" + processEnv
        + ", processOutput=" + processOutput
        + ", supportConfig=" + supportConfig
        + ", commandName=" + commandName
        + ", systemEnv=" + systemEnv
        + ", persistentBaseDir=" + persistentBaseDir
        + ", downloadCache=" + downloadCache
        + ", extractedFileSetStore=" + extractedFileSetStore
        + ", downloadPackage=" + downloadPackage
        + ", progressListener=" + progressListener
        + ", extractPackage=" + extractPackage
        + ", packageOfDistribution=" + packageOfDistribution
        + ", mongodArguments=" + mongodArguments
        + ", net=" + net
        + ", databaseDir=" + databaseDir
        + ", mongodProcessArguments=" + mongodProcessArguments
        + "}";
  }

  /**
   * Creates an immutable copy of a {@link Mongod} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable Mongod instance
   */
  public static ImmutableMongod copyOf(Mongod instance) {
    if (instance instanceof ImmutableMongod) {
      return (ImmutableMongod) instance;
    }
    return ImmutableMongod.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableMongod ImmutableMongod}.
   * <pre>
   * ImmutableMongod.builder()
   *    .initTempDirectory(de.flapdoodle.embed.process.transitions.InitTempDirectory) // optional {@link Mongod#initTempDirectory() initTempDirectory}
   *    .processWorkingDir(de.flapdoodle.reverse.Transition&amp;lt;de.flapdoodle.embed.process.types.ProcessWorkingDir&amp;gt;) // optional {@link Mongod#processWorkingDir() processWorkingDir}
   *    .distributionBaseUrl(de.flapdoodle.reverse.Transition&amp;lt;de.flapdoodle.embed.mongo.types.DistributionBaseUrl&amp;gt;) // optional {@link Mongod#distributionBaseUrl() distributionBaseUrl}
   *    .platform(de.flapdoodle.reverse.Transition&amp;lt;de.flapdoodle.os.Platform&amp;gt;) // optional {@link Mongod#platform() platform}
   *    .distribution(de.flapdoodle.reverse.Transition&amp;lt;de.flapdoodle.embed.process.distribution.Distribution&amp;gt;) // optional {@link Mongod#distribution() distribution}
   *    .processConfig(de.flapdoodle.reverse.transitions.ImmutableStart&amp;lt;de.flapdoodle.embed.process.types.ProcessConfig&amp;gt;) // optional {@link Mongod#processConfig() processConfig}
   *    .processEnv(de.flapdoodle.reverse.Transition&amp;lt;de.flapdoodle.embed.process.types.ProcessEnv&amp;gt;) // optional {@link Mongod#processEnv() processEnv}
   *    .processOutput(de.flapdoodle.reverse.Transition&amp;lt;de.flapdoodle.embed.process.io.ProcessOutput&amp;gt;) // optional {@link Mongod#processOutput() processOutput}
   *    .supportConfig(de.flapdoodle.reverse.Transition&amp;lt;de.flapdoodle.embed.process.config.SupportConfig&amp;gt;) // optional {@link Mongod#supportConfig() supportConfig}
   *    .commandName(de.flapdoodle.reverse.Transition&amp;lt;de.flapdoodle.embed.process.types.Name&amp;gt;) // optional {@link Mongod#commandName() commandName}
   *    .systemEnv(de.flapdoodle.reverse.Transition&amp;lt;de.flapdoodle.embed.mongo.types.SystemEnv&amp;gt;) // optional {@link Mongod#systemEnv() systemEnv}
   *    .persistentBaseDir(de.flapdoodle.reverse.Transition&amp;lt;de.flapdoodle.embed.process.io.directories.PersistentDir&amp;gt;) // optional {@link Mongod#persistentBaseDir() persistentBaseDir}
   *    .downloadCache(de.flapdoodle.reverse.Transition&amp;lt;de.flapdoodle.embed.process.store.DownloadCache&amp;gt;) // optional {@link Mongod#downloadCache() downloadCache}
   *    .extractedFileSetStore(de.flapdoodle.reverse.Transition&amp;lt;de.flapdoodle.embed.process.store.ExtractedFileSetStore&amp;gt;) // optional {@link Mongod#extractedFileSetStore() extractedFileSetStore}
   *    .downloadPackage(de.flapdoodle.embed.process.transitions.DownloadPackage) // optional {@link Mongod#downloadPackage() downloadPackage}
   *    .progressListener(de.flapdoodle.reverse.Transition&amp;lt;de.flapdoodle.embed.process.io.progress.ProgressListener&amp;gt;) // optional {@link Mongod#progressListener() progressListener}
   *    .extractPackage(de.flapdoodle.reverse.Transition&amp;lt;de.flapdoodle.embed.process.archives.ExtractedFileSet&amp;gt;) // optional {@link Mongod#extractPackage() extractPackage}
   *    .packageOfDistribution(de.flapdoodle.reverse.Transition&amp;lt;de.flapdoodle.embed.process.config.store.Package&amp;gt;) // optional {@link Mongod#packageOfDistribution() packageOfDistribution}
   *    .mongodArguments(de.flapdoodle.reverse.Transition&amp;lt;de.flapdoodle.embed.mongo.commands.MongodArguments&amp;gt;) // optional {@link Mongod#mongodArguments() mongodArguments}
   *    .net(de.flapdoodle.reverse.Transition&amp;lt;de.flapdoodle.embed.mongo.config.Net&amp;gt;) // optional {@link Mongod#net() net}
   *    .databaseDir(de.flapdoodle.reverse.Transition&amp;lt;de.flapdoodle.embed.mongo.types.DatabaseDir&amp;gt;) // optional {@link Mongod#databaseDir() databaseDir}
   *    .mongodProcessArguments(de.flapdoodle.embed.mongo.transitions.MongodProcessArguments) // optional {@link Mongod#mongodProcessArguments() mongodProcessArguments}
   *    .build();
   * </pre>
   * @return A new ImmutableMongod builder
   */
  public static ImmutableMongod.Builder builder() {
    return new ImmutableMongod.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableMongod ImmutableMongod}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  public static final class Builder {
    private InitTempDirectory initTempDirectory;
    private Transition<ProcessWorkingDir> processWorkingDir;
    private Transition<DistributionBaseUrl> distributionBaseUrl;
    private Transition<Platform> platform;
    private Transition<Distribution> distribution;
    private ImmutableStart<ProcessConfig> processConfig;
    private Transition<ProcessEnv> processEnv;
    private Transition<ProcessOutput> processOutput;
    private Transition<SupportConfig> supportConfig;
    private Transition<Name> commandName;
    private Transition<SystemEnv> systemEnv;
    private Transition<PersistentDir> persistentBaseDir;
    private Transition<DownloadCache> downloadCache;
    private Transition<ExtractedFileSetStore> extractedFileSetStore;
    private DownloadPackage downloadPackage;
    private Transition<ProgressListener> progressListener;
    private Transition<ExtractedFileSet> extractPackage;
    private Transition<Package> packageOfDistribution;
    private Transition<MongodArguments> mongodArguments;
    private Transition<Net> net;
    private Transition<DatabaseDir> databaseDir;
    private MongodProcessArguments mongodProcessArguments;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.flapdoodle.embed.mongo.transitions.ExtractFileSet} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(ExtractFileSet instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.flapdoodle.embed.mongo.transitions.CommandName} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(CommandName instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.flapdoodle.embed.mongo.transitions.Mongod} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(Mongod instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.flapdoodle.embed.mongo.transitions.ProcessDefaults} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(ProcessDefaults instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.flapdoodle.embed.mongo.transitions.WorkspaceDefaults} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(WorkspaceDefaults instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.flapdoodle.embed.mongo.transitions.VersionAndPlatform} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(VersionAndPlatform instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    private void from(Object object) {
      long bits = 0;
      if (object instanceof ExtractFileSet) {
        ExtractFileSet instance = (ExtractFileSet) object;
        if ((bits & 0x40L) == 0) {
          progressListener(instance.progressListener());
          bits |= 0x40L;
        }
        if ((bits & 0x100L) == 0) {
          systemEnv(instance.systemEnv());
          bits |= 0x100L;
        }
        if ((bits & 0x200L) == 0) {
          packageOfDistribution(instance.packageOfDistribution());
          bits |= 0x200L;
        }
        if ((bits & 0x800L) == 0) {
          extractedFileSetStore(instance.extractedFileSetStore());
          bits |= 0x800L;
        }
        if ((bits & 0x2000L) == 0) {
          extractPackage(instance.extractPackage());
          bits |= 0x2000L;
        }
        if ((bits & 0x2L) == 0) {
          downloadCache(instance.downloadCache());
          bits |= 0x2L;
        }
        if ((bits & 0x4L) == 0) {
          persistentBaseDir(instance.persistentBaseDir());
          bits |= 0x4L;
        }
        if ((bits & 0x20L) == 0) {
          downloadPackage(instance.downloadPackage());
          bits |= 0x20L;
        }
      }
      if (object instanceof CommandName) {
        CommandName instance = (CommandName) object;
        if ((bits & 0x80L) == 0) {
          commandName(instance.commandName());
          bits |= 0x80L;
        }
      }
      if (object instanceof Mongod) {
        Mongod instance = (Mongod) object;
        if ((bits & 0x1L) == 0) {
          processConfig(instance.processConfig());
          bits |= 0x1L;
        }
        if ((bits & 0x2L) == 0) {
          downloadCache(instance.downloadCache());
          bits |= 0x2L;
        }
        if ((bits & 0x4L) == 0) {
          persistentBaseDir(instance.persistentBaseDir());
          bits |= 0x4L;
        }
        if ((bits & 0x8L) == 0) {
          distribution(instance.distribution());
          bits |= 0x8L;
        }
        if ((bits & 0x10L) == 0) {
          platform(instance.platform());
          bits |= 0x10L;
        }
        if ((bits & 0x20L) == 0) {
          downloadPackage(instance.downloadPackage());
          bits |= 0x20L;
        }
        if ((bits & 0x40L) == 0) {
          progressListener(instance.progressListener());
          bits |= 0x40L;
        }
        if ((bits & 0x80L) == 0) {
          commandName(instance.commandName());
          bits |= 0x80L;
        }
        if ((bits & 0x100L) == 0) {
          systemEnv(instance.systemEnv());
          bits |= 0x100L;
        }
        mongodProcessArguments(instance.mongodProcessArguments());
        if ((bits & 0x200L) == 0) {
          packageOfDistribution(instance.packageOfDistribution());
          bits |= 0x200L;
        }
        mongodArguments(instance.mongodArguments());
        if ((bits & 0x400L) == 0) {
          supportConfig(instance.supportConfig());
          bits |= 0x400L;
        }
        if ((bits & 0x800L) == 0) {
          extractedFileSetStore(instance.extractedFileSetStore());
          bits |= 0x800L;
        }
        if ((bits & 0x1000L) == 0) {
          initTempDirectory(instance.initTempDirectory());
          bits |= 0x1000L;
        }
        if ((bits & 0x2000L) == 0) {
          extractPackage(instance.extractPackage());
          bits |= 0x2000L;
        }
        if ((bits & 0x4000L) == 0) {
          processEnv(instance.processEnv());
          bits |= 0x4000L;
        }
        if ((bits & 0x8000L) == 0) {
          processOutput(instance.processOutput());
          bits |= 0x8000L;
        }
        databaseDir(instance.databaseDir());
        net(instance.net());
        if ((bits & 0x10000L) == 0) {
          processWorkingDir(instance.processWorkingDir());
          bits |= 0x10000L;
        }
        if ((bits & 0x20000L) == 0) {
          distributionBaseUrl(instance.distributionBaseUrl());
          bits |= 0x20000L;
        }
      }
      if (object instanceof ProcessDefaults) {
        ProcessDefaults instance = (ProcessDefaults) object;
        if ((bits & 0x8000L) == 0) {
          processOutput(instance.processOutput());
          bits |= 0x8000L;
        }
        if ((bits & 0x400L) == 0) {
          supportConfig(instance.supportConfig());
          bits |= 0x400L;
        }
        if ((bits & 0x1L) == 0) {
          processConfig(instance.processConfig());
          bits |= 0x1L;
        }
        if ((bits & 0x4000L) == 0) {
          processEnv(instance.processEnv());
          bits |= 0x4000L;
        }
      }
      if (object instanceof WorkspaceDefaults) {
        WorkspaceDefaults instance = (WorkspaceDefaults) object;
        if ((bits & 0x10000L) == 0) {
          processWorkingDir(instance.processWorkingDir());
          bits |= 0x10000L;
        }
        if ((bits & 0x1000L) == 0) {
          initTempDirectory(instance.initTempDirectory());
          bits |= 0x1000L;
        }
        if ((bits & 0x20000L) == 0) {
          distributionBaseUrl(instance.distributionBaseUrl());
          bits |= 0x20000L;
        }
      }
      if (object instanceof VersionAndPlatform) {
        VersionAndPlatform instance = (VersionAndPlatform) object;
        if ((bits & 0x8L) == 0) {
          distribution(instance.distribution());
          bits |= 0x8L;
        }
        if ((bits & 0x10L) == 0) {
          platform(instance.platform());
          bits |= 0x10L;
        }
      }
    }

    /**
     * Initializes the value for the {@link Mongod#initTempDirectory() initTempDirectory} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link Mongod#initTempDirectory() initTempDirectory}.</em>
     * @param initTempDirectory The value for initTempDirectory 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder initTempDirectory(InitTempDirectory initTempDirectory) {
      this.initTempDirectory = Objects.requireNonNull(initTempDirectory, "initTempDirectory");
      return this;
    }

    /**
     * Initializes the value for the {@link Mongod#processWorkingDir() processWorkingDir} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link Mongod#processWorkingDir() processWorkingDir}.</em>
     * @param processWorkingDir The value for processWorkingDir 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder processWorkingDir(Transition<ProcessWorkingDir> processWorkingDir) {
      this.processWorkingDir = Objects.requireNonNull(processWorkingDir, "processWorkingDir");
      return this;
    }

    /**
     * Initializes the value for the {@link Mongod#distributionBaseUrl() distributionBaseUrl} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link Mongod#distributionBaseUrl() distributionBaseUrl}.</em>
     * @param distributionBaseUrl The value for distributionBaseUrl 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder distributionBaseUrl(Transition<DistributionBaseUrl> distributionBaseUrl) {
      this.distributionBaseUrl = Objects.requireNonNull(distributionBaseUrl, "distributionBaseUrl");
      return this;
    }

    /**
     * Initializes the value for the {@link Mongod#platform() platform} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link Mongod#platform() platform}.</em>
     * @param platform The value for platform 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder platform(Transition<Platform> platform) {
      this.platform = Objects.requireNonNull(platform, "platform");
      return this;
    }

    /**
     * Initializes the value for the {@link Mongod#distribution() distribution} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link Mongod#distribution() distribution}.</em>
     * @param distribution The value for distribution 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder distribution(Transition<Distribution> distribution) {
      this.distribution = Objects.requireNonNull(distribution, "distribution");
      return this;
    }

    /**
     * Initializes the value for the {@link Mongod#processConfig() processConfig} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link Mongod#processConfig() processConfig}.</em>
     * @param processConfig The value for processConfig 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder processConfig(ImmutableStart<ProcessConfig> processConfig) {
      this.processConfig = Objects.requireNonNull(processConfig, "processConfig");
      return this;
    }

    /**
     * Initializes the value for the {@link Mongod#processEnv() processEnv} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link Mongod#processEnv() processEnv}.</em>
     * @param processEnv The value for processEnv 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder processEnv(Transition<ProcessEnv> processEnv) {
      this.processEnv = Objects.requireNonNull(processEnv, "processEnv");
      return this;
    }

    /**
     * Initializes the value for the {@link Mongod#processOutput() processOutput} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link Mongod#processOutput() processOutput}.</em>
     * @param processOutput The value for processOutput 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder processOutput(Transition<ProcessOutput> processOutput) {
      this.processOutput = Objects.requireNonNull(processOutput, "processOutput");
      return this;
    }

    /**
     * Initializes the value for the {@link Mongod#supportConfig() supportConfig} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link Mongod#supportConfig() supportConfig}.</em>
     * @param supportConfig The value for supportConfig 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder supportConfig(Transition<SupportConfig> supportConfig) {
      this.supportConfig = Objects.requireNonNull(supportConfig, "supportConfig");
      return this;
    }

    /**
     * Initializes the value for the {@link Mongod#commandName() commandName} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link Mongod#commandName() commandName}.</em>
     * @param commandName The value for commandName 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder commandName(Transition<Name> commandName) {
      this.commandName = Objects.requireNonNull(commandName, "commandName");
      return this;
    }

    /**
     * Initializes the value for the {@link Mongod#systemEnv() systemEnv} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link Mongod#systemEnv() systemEnv}.</em>
     * @param systemEnv The value for systemEnv 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder systemEnv(Transition<SystemEnv> systemEnv) {
      this.systemEnv = Objects.requireNonNull(systemEnv, "systemEnv");
      return this;
    }

    /**
     * Initializes the value for the {@link Mongod#persistentBaseDir() persistentBaseDir} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link Mongod#persistentBaseDir() persistentBaseDir}.</em>
     * @param persistentBaseDir The value for persistentBaseDir 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder persistentBaseDir(Transition<PersistentDir> persistentBaseDir) {
      this.persistentBaseDir = Objects.requireNonNull(persistentBaseDir, "persistentBaseDir");
      return this;
    }

    /**
     * Initializes the value for the {@link Mongod#downloadCache() downloadCache} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link Mongod#downloadCache() downloadCache}.</em>
     * @param downloadCache The value for downloadCache 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder downloadCache(Transition<DownloadCache> downloadCache) {
      this.downloadCache = Objects.requireNonNull(downloadCache, "downloadCache");
      return this;
    }

    /**
     * Initializes the value for the {@link Mongod#extractedFileSetStore() extractedFileSetStore} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link Mongod#extractedFileSetStore() extractedFileSetStore}.</em>
     * @param extractedFileSetStore The value for extractedFileSetStore 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder extractedFileSetStore(Transition<ExtractedFileSetStore> extractedFileSetStore) {
      this.extractedFileSetStore = Objects.requireNonNull(extractedFileSetStore, "extractedFileSetStore");
      return this;
    }

    /**
     * Initializes the value for the {@link Mongod#downloadPackage() downloadPackage} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link Mongod#downloadPackage() downloadPackage}.</em>
     * @param downloadPackage The value for downloadPackage 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder downloadPackage(DownloadPackage downloadPackage) {
      this.downloadPackage = Objects.requireNonNull(downloadPackage, "downloadPackage");
      return this;
    }

    /**
     * Initializes the value for the {@link Mongod#progressListener() progressListener} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link Mongod#progressListener() progressListener}.</em>
     * @param progressListener The value for progressListener 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder progressListener(Transition<ProgressListener> progressListener) {
      this.progressListener = Objects.requireNonNull(progressListener, "progressListener");
      return this;
    }

    /**
     * Initializes the value for the {@link Mongod#extractPackage() extractPackage} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link Mongod#extractPackage() extractPackage}.</em>
     * @param extractPackage The value for extractPackage 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder extractPackage(Transition<ExtractedFileSet> extractPackage) {
      this.extractPackage = Objects.requireNonNull(extractPackage, "extractPackage");
      return this;
    }

    /**
     * Initializes the value for the {@link Mongod#packageOfDistribution() packageOfDistribution} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link Mongod#packageOfDistribution() packageOfDistribution}.</em>
     * @param packageOfDistribution The value for packageOfDistribution 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder packageOfDistribution(Transition<Package> packageOfDistribution) {
      this.packageOfDistribution = Objects.requireNonNull(packageOfDistribution, "packageOfDistribution");
      return this;
    }

    /**
     * Initializes the value for the {@link Mongod#mongodArguments() mongodArguments} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link Mongod#mongodArguments() mongodArguments}.</em>
     * @param mongodArguments The value for mongodArguments 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder mongodArguments(Transition<MongodArguments> mongodArguments) {
      this.mongodArguments = Objects.requireNonNull(mongodArguments, "mongodArguments");
      return this;
    }

    /**
     * Initializes the value for the {@link Mongod#net() net} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link Mongod#net() net}.</em>
     * @param net The value for net 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder net(Transition<Net> net) {
      this.net = Objects.requireNonNull(net, "net");
      return this;
    }

    /**
     * Initializes the value for the {@link Mongod#databaseDir() databaseDir} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link Mongod#databaseDir() databaseDir}.</em>
     * @param databaseDir The value for databaseDir 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder databaseDir(Transition<DatabaseDir> databaseDir) {
      this.databaseDir = Objects.requireNonNull(databaseDir, "databaseDir");
      return this;
    }

    /**
     * Initializes the value for the {@link Mongod#mongodProcessArguments() mongodProcessArguments} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link Mongod#mongodProcessArguments() mongodProcessArguments}.</em>
     * @param mongodProcessArguments The value for mongodProcessArguments 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder mongodProcessArguments(MongodProcessArguments mongodProcessArguments) {
      this.mongodProcessArguments = Objects.requireNonNull(mongodProcessArguments, "mongodProcessArguments");
      return this;
    }

    /**
     * Builds a new {@link ImmutableMongod ImmutableMongod}.
     * @return An immutable instance of Mongod
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableMongod build() {
      return new ImmutableMongod(this);
    }
  }
}

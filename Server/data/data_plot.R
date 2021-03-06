setwd("/Users/vedantdasswain/GitRepositories/CS7470/Server/data")

library("dplyr")
library("car")
library("ggplot2")

options("scipen"=100, "digits"=4)

## Synchronising with peaks
accel_pos_a <- read.csv("peaks_old/accel_pos_a.csv", na.strings=c("", "NA"), stringsAsFactors = FALSE)
accel_pos_b <- read.csv("peaks_old/accel_pos_b.csv", na.strings=c("", "NA"), stringsAsFactors = FALSE)
accel_pos_x <- read.csv("peaks_old/accel_pos_x.csv", na.strings=c("", "NA"), stringsAsFactors = FALSE)

accel_pos_ab <- merge(x = accel_pos_a, y = accel_pos_b, all = TRUE)
accel_pos_abx <- merge(x = accel_pos_ab, y = accel_pos_x, all = TRUE)

accel_pos_mag <- accel_pos_abx %>% mutate(force=sqrt(x^2+y^2+z^2))

time_med <- median(accel_pos_mag$timestamp)
time_max <- max(accel_pos_mag$timestamp)
time_min <- min(accel_pos_mag$timestamp)
time_quart <- (time_max+time_med)/2
  
accel_plot <- ggplot(accel_pos_mag, aes(x = timestamp,
                                                    y = force, group=position, colour=position)) +
  geom_line() +
  labs(title="Sync",x = "Time", y = "Magnitude") +
  xlim(time_min,time_max) +
  theme_minimal() +
  theme(axis.text.x = element_text(angle = 90, hjust = 0,size=1),
        panel.grid.major = element_blank(),
        panel.grid.minor = element_blank(),
        plot.title = element_text(size = rel(1.5), face = "bold", vjust = 1.5))

print(accel_plot)

accel_pos_peaks <- accel_pos_mag %>% group_by(position) %>% filter(force == max(force))

## Data on flex static
accel_pos_a <- read.csv("activities/flex_stat_q/accel_pos_a.csv", na.strings=c("", "NA"), stringsAsFactors = FALSE)
accel_pos_b <- read.csv("activities/flex_stat_q/accel_pos_b.csv", na.strings=c("", "NA"), stringsAsFactors = FALSE)
accel_pos_x <- read.csv("activities/flex_stat_q/accel_pos_x.csv", na.strings=c("", "NA"), stringsAsFactors = FALSE)

accel_pos_ab <- merge(x = accel_pos_a, y = accel_pos_b, all = TRUE)
accel_pos_abx <- merge(x = accel_pos_ab, y = accel_pos_x, all = TRUE)

accel_pos_mag <- accel_pos_abx %>% mutate(force=sqrt(x^2+y^2+z^2))

time_med <- median(accel_pos_mag$timestamp)
time_max <- max(accel_pos_mag$timestamp)
time_quart <- (time_max+time_med)/2

accel_plot <- ggplot(accel_pos_mag, aes(x = timestamp,
                                        y = force, group=position, colour=position)) +
  geom_line() +
  labs(title="",x = "Time", y = "Magnitude") +
  # xlim(time_quart,time_max) +
  theme_minimal() +
  theme(axis.text.x = element_text(angle = 90, hjust = 0,size=1),
        panel.grid.major = element_blank(),
        panel.grid.minor = element_blank(),
        plot.title = element_text(size = rel(1.5), face = "bold", vjust = 1.5))

print(accel_plot)

## Data on flex move
accel_pos_a <- read.csv("activities/flex_move/accel_pos_a.csv", na.strings=c("", "NA"), stringsAsFactors = FALSE)
accel_pos_b <- read.csv("activities/flex_move/accel_pos_b.csv", na.strings=c("", "NA"), stringsAsFactors = FALSE)
accel_pos_x <- read.csv("activities/flex_move/accel_pos_x.csv", na.strings=c("", "NA"), stringsAsFactors = FALSE)

accel_pos_ab <- merge(x = accel_pos_a, y = accel_pos_b, all = TRUE)
accel_pos_abx <- merge(x = accel_pos_ab, y = accel_pos_x, all = TRUE)

accel_pos_mag <- accel_pos_abx %>% mutate(force=sqrt(x^2+y^2+z^2))

time_med <- median(accel_pos_mag$timestamp)
time_max <- max(accel_pos_mag$timestamp)
time_quart <- (time_max+time_med)/2

accel_plot <- ggplot(accel_pos_mag, aes(x = timestamp,
                                        y = force, group=position, colour=position)) +
  geom_line() +
  labs(title="",x = "Time", y = "Magnitude") +
  # xlim(time_quart,time_max) +
  theme_minimal() +
  theme(axis.text.x = element_text(angle = 90, hjust = 0,size=1),
        panel.grid.major = element_blank(),
        panel.grid.minor = element_blank(),
        plot.title = element_text(size = rel(1.5), face = "bold", vjust = 1.5))

print(accel_plot)

## Data on walk
accel_pos_a <- read.csv("activities/walk/accel_pos_a.csv", na.strings=c("", "NA"), stringsAsFactors = FALSE)
accel_pos_b <- read.csv("activities/walk/accel_pos_b.csv", na.strings=c("", "NA"), stringsAsFactors = FALSE)
accel_pos_x <- read.csv("activities/walk/accel_pos_x.csv", na.strings=c("", "NA"), stringsAsFactors = FALSE)

accel_pos_ab <- merge(x = accel_pos_a, y = accel_pos_b, all = TRUE)
accel_pos_abx <- merge(x = accel_pos_ab, y = accel_pos_x, all = TRUE)

accel_pos_mag <- accel_pos_abx %>% mutate(force=sqrt(x^2+y^2+z^2))

time_med <- median(accel_pos_mag$timestamp)
time_max <- max(accel_pos_mag$timestamp)
time_quart <- (time_max+time_med)/2

accel_plot <- ggplot(accel_pos_mag, aes(x = timestamp,
                                        y = force, group=position, colour=position)) +
  geom_line() +
  labs(title="Flex Walk",x = "Time", y = "Magnitude") +
  # xlim(time_quart,time_max) +
  theme_minimal() +
  theme(axis.text.x = element_text(angle = 90, hjust = 0,size=1),
        panel.grid.major = element_blank(),
        panel.grid.minor = element_blank(),
        plot.title = element_text(size = rel(1.5), face = "bold", vjust = 1.5))

print(accel_plot)
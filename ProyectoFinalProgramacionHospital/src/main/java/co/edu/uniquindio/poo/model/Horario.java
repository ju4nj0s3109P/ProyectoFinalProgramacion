package co.edu.uniquindio.poo.model;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record Horario(DayOfWeek dia, LocalTime horaInicio, LocalTime horaFin) {}

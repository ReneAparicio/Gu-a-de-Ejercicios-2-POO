<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registro de Cliente - Car Clean</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <style>
        .form-section {
            background: #f8f9fa;
            border-radius: 10px;
            padding: 1.5rem;
            margin-bottom: 2rem;
            border: 1px solid #dee2e6;
        }
        .section-title {
            color: #0d6efd;
            margin-bottom: 1.5rem;
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }
        .price-tag {
            background: #198754;
            color: white;
            padding: 2px 8px;
            border-radius: 4px;
            font-size: 0.9em;
            margin-left: 8px;
        }
        .vip-checkbox {
            padding: 1rem;
            background: #fff3cd;
            border-radius: 8px;
            margin-top: 1rem;
        }
        .form-control:focus {
            box-shadow: 0 0 0 0.25rem rgba(13, 110, 253, 0.25);
        }
    </style>
</head>
<body class="bg-light">
<div class="container py-5">
    <div class="row justify-content-center">
        <div class="col-md-10 col-lg-8">
            <div class="card shadow-lg">
                <div class="card-header bg-primary text-white">
                    <h2 class="text-center mb-0"><i class="bi bi-car-front"></i> Car Clean - Registro de Cliente</h2>
                </div>
                <div class="card-body p-4">
                    <% if (request.getAttribute("error") != null) { %>
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <i class="bi bi-exclamation-triangle me-2"></i>
                        <%= request.getAttribute("error") %>
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                    <% } %>

                    <form action="ProcesarCliente" method="post">
                        <!-- Sección Datos Personales -->
                        <div class="form-section">
                            <h5 class="section-title">
                                <i class="bi bi-person-circle"></i>
                                Datos Personales
                            </h5>

                            <div class="row g-3">
                                <div class="col-md-6">
                                    <div class="form-floating">
                                        <input type="text" class="form-control ${request.getAttribute('error_nombre') != null ? 'is-invalid' : ''}"
                                               id="nombre" name="nombre" value="${param.nombre}"
                                               placeholder="Nombre" required>
                                        <label for="nombre">Nombre</label>
                                        <% if (request.getAttribute("error_nombre") != null) { %>
                                        <div class="invalid-feedback">
                                            <%= request.getAttribute("error_nombre") %>
                                        </div>
                                        <% } %>
                                    </div>
                                </div>

                                <div class="col-md-6">
                                    <div class="form-floating">
                                        <input type="text" class="form-control ${request.getAttribute('error_apellidos') != null ? 'is-invalid' : ''}"
                                               id="apellidos" name="apellidos" value="${param.apellidos}"
                                               placeholder="Apellidos" required>
                                        <label for="apellidos">Apellidos</label>
                                        <% if (request.getAttribute("error_apellidos") != null) { %>
                                        <div class="invalid-feedback">
                                            <%= request.getAttribute("error_apellidos") %>
                                        </div>
                                        <% } %>
                                    </div>
                                </div>
                            </div>

                            <div class="vip-checkbox mt-3">
                                <div class="form-check form-switch">
                                    <input class="form-check-input" type="checkbox" role="switch"
                                           id="vip" name="vip" ${param.vip != null ? 'checked' : ''}>
                                    <label class="form-check-label" for="vip">
                                        <i class="bi bi-star-fill text-warning"></i> Cliente VIP
                                    </label>
                                </div>
                            </div>
                        </div>

                        <!-- Sección Vehículo -->
                        <div class="form-section">
                            <h5 class="section-title">
                                <i class="bi bi-car-front"></i>
                                Datos del Automotor
                            </h5>

                            <div class="row g-3">
                                <div class="col-md-6">
                                    <div class="form-floating">
                                        <input type="text" class="form-control ${request.getAttribute('error_marca') != null ? 'is-invalid' : ''}"
                                               id="marca" name="marca" value="${param.marca}"
                                               placeholder="Marca" required>
                                        <label for="marca">Marca</label>
                                        <% if (request.getAttribute("error_marca") != null) { %>
                                        <div class="invalid-feedback">
                                            <%= request.getAttribute("error_marca") %>
                                        </div>
                                        <% } %>
                                    </div>
                                </div>

                                <div class="col-md-6">
                                    <div class="form-floating">
                                        <input type="text" class="form-control ${request.getAttribute('error_modelo') != null ? 'is-invalid' : ''}"
                                               id="modelo" name="modelo" value="${param.modelo}"
                                               placeholder="Modelo" required>
                                        <label for="modelo">Modelo</label>
                                        <% if (request.getAttribute("error_modelo") != null) { %>
                                        <div class="invalid-feedback">
                                            <%= request.getAttribute("error_modelo") %>
                                        </div>
                                        <% } %>
                                    </div>
                                </div>

                                <div class="col-md-6">
                                    <div class="form-floating">
                                        <input type="number" class="form-control ${request.getAttribute('error_year') != null ? 'is-invalid' : ''}"
                                               id="year" name="year" value="${param.year}"
                                               min="1900" max="2025" placeholder="Año" required>
                                        <label for="year">Año</label>
                                        <% if (request.getAttribute("error_year") != null) { %>
                                        <div class="invalid-feedback">
                                            <%= request.getAttribute("error_year") %>
                                        </div>
                                        <% } %>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Sección Servicio -->
                        <div class="form-section">
                            <h5 class="section-title">
                                <i class="bi bi-list-check"></i>
                                Tipo de Servicio
                            </h5>

                            <div class="form-floating">
                                <select class="form-select ${request.getAttribute('error_tipo_servicio') != null ? 'is-invalid' : ''}"
                                        id="tipo_servicio" name="tipo_servicio" required>
                                    <option value="">Seleccione un servicio</option>
                                    <option value="Motocicleta" ${param.tipo_servicio == 'Motocicleta' ? 'selected' : ''}>
                                        Motocicleta <span class="price-tag">$2.75</span>
                                    </option>
                                    <option value="Sedan" ${param.tipo_servicio == 'Sedan' ? 'selected' : ''}>
                                        Sedán <span class="price-tag">$3.50</span>
                                    </option>
                                    <option value="Camioneta" ${param.tipo_servicio == 'Camioneta' ? 'selected' : ''}>
                                        Camioneta <span class="price-tag">$4.00</span>
                                    </option>
                                    <option value="Microbus" ${param.tipo_servicio == 'Microbus' ? 'selected' : ''}>
                                        Microbús <span class="price-tag">$5.00</span>
                                    </option>
                                    <option value="Autobus" ${param.tipo_servicio == 'Autobus' ? 'selected' : ''}>
                                        Autobús <span class="price-tag">$7.00</span>
                                    </option>
                                </select>
                                <label for="tipo_servicio">Seleccione el servicio</label>
                                <% if (request.getAttribute("error_tipo_servicio") != null) { %>
                                <div class="invalid-feedback">
                                    <%= request.getAttribute("error_tipo_servicio") %>
                                </div>
                                <% } %>
                            </div>
                        </div>

                        <div class="d-flex gap-3 justify-content-end mt-4">
                            <button type="submit" class="btn btn-success btn-lg">
                                <i class="bi bi-check2-circle me-2"></i>Registrar Cliente
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
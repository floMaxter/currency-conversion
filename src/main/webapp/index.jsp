<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
        <title>Currency Exchange Service</title>
    </head>
    <body>
        <div class="container">
            <h1 class="mb-4">Currency Exchange Service</h1>

            <section class="mt-xxl-5">
                <div class="row">
                    <div class="col col-6">
                        <h4>Available Currencies</h4>
                        <table class="table currencies-table">
                            <thead>
                                <tr>
                                    <th>Code</th>
                                    <th>Name</th>
                                    <th>Sign</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>USD</td>
                                    <td>US Dollar</td>
                                    <td>$</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>

                    <div class="col col-6">
                        <h4>Add New Currency</h4>
                        <form id="add-currency">
                            <div class="form-group mt-3">
                                <label for="add-currency-code">Code</label>
                                <input type="text" name="code" placeholder="USD"
                                       class="form-control form-control-sm currency-code" id="add-currency-code">
                            </div>
                            <div class="form-group mt-3">
                                <label for="add-currency-name">Name</label>
                                <input type="text" name="name" placeholder="US Dollar"
                                       class="form-control form-control-sm" id="add-currency-name">
                            </div>
                            <div class="form-group mt-3">
                                <label for="add-currency-sign">Sign</label>
                                <input type="text" name="sign" placeholder="$" class="form-control form-control-sm"
                                       id="add-currency-sign">
                            </div>
                            <button type="submit" class="btn btn-primary btn-sm mt-4">Add Currency</button>
                        </form>
                    </div>
                </div>
            </section>

            <section class="mt-xxl-5">
                <h2>Exchange Rates</h2>
                <table class="table exchange-rates-table">
                    <thead>
                        <tr>
                            <th>Currency</th>
                            <th>Exchange Rate</th>
                            <th>Edit</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>USDRUB</td>
                            <td>1.00</td>
                            <td>
                                <button type="button" class="btn btn-secondary btn-sm exchange-rate-edit"
                                        data-bs-toggle="modal"
                                        data-bs-target="#edit-exchange-rate-modal">Edit
                                </button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </section>

            <!-- Edit exchange rate modal -->
            <div class="modal fade" id="edit-exchange-rate-modal" tabindex="-1" role="dialog">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Edit Exchange Rate</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form>
                                <div class="form-group">
                                    <label for="exchange-rate-input">Exchange Rate:</label>
                                    <input type="text" class="form-control" id="exchange-rate-input" value="1.00">
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            <button type="button" class="btn btn-primary">Save changes</button>
                        </div>
                    </div>
                </div>
            </div>

            <section class="mt-xxl-5 pb-5">
                <div class="row">
                    <div class="col col-6">
                        <h2>Add New Exchange Rate</h2>
                        <form id="add-exchange-rate">
                            <div class="form-group">
                                <label for="new-rate-base-currency">Base currency</label>
                                <select class="form-control" name="baseCurrencyCode" id="new-rate-base-currency">
                                    <option value="USD">USD</option>
                                </select>
                            </div>
                            <div class="form-group mt-3">
                                <label for="new-rate-target-currency">Target currency</label>
                                <select class="form-control" name="targetCurrencyCode" id="new-rate-target-currency">
                                    <option value="USD">USD</option>
                                </select>
                            </div>
                            <div class="form-group mt-3">
                                <label for="exchange-rate">Exchange Rate</label>
                                <input type="text" placeholder="1.23" class="form-control" name="rate"
                                       id="exchange-rate">
                            </div>
                            <button type="submit" class="btn btn-primary mt-3">Add Exchange Rate</button>
                        </form>
                    </div>

                    <div class="col col-6"><h2>Currency Conversion</h2>
                        <form id="convert">
                            <div class="form-group">
                                <label for="convert-base-currency">Base Currency</label>
                                <select class="form-control" id="convert-base-currency">
                                    <option value="USD">USD</option>
                                </select>
                            </div>
                            <div class="form-group mt-3">
                                <label for="convert-target-currency">Target Currency</label>
                                <select class="form-control" id="convert-target-currency">
                                    <option value="USD">USD</option>
                                </select>
                            </div>
                            <div class="row">
                                <div class="col-6">
                                    <div class="form-group mt-3">
                                        <label for="convert-amount">Amount</label>
                                        <input placeholder="1.23" type="text" class="form-control" id="convert-amount">
                                    </div>
                                </div>
                                <div class="col-6">
                                    <div class="form-group mt-3">
                                        <label for="convert-converted-amount">Converted amount</label>
                                        <input type="text" class="form-control" id="convert-converted-amount">
                                    </div>
                                </div>
                            </div>
                            <button type="submit" class="btn btn-primary mt-3">Convert</button>
                        </form>
                    </div>
                </div>
            </section>

            <div class="position-fixed bottom-0 end-0 p-3" style="z-index: 11">
                <div id="api-error-toast" class="toast hide" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="toast-header">
                        <strong class="me-auto">Error</strong>
                        <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>

                    </div>
                    <div class="toast-body">

                    </div>
                </div>
            </div>
        </div>

        <script src="js/jquery-3.6.3.min.js"></script>
        <script src="js/bootstrap.js"></script>
        <script src="js/app.js"></script>
    </body>
</html>